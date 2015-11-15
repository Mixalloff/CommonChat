fs = require ('fs')
utils = require('../utils/utils.coffee')

class Router
	constructor: (@server) ->
		try
			@config = JSON.parse fs.readFileSync "configs/route.json"
			@controllers = {}

			for controller in Object.keys(@config.controllers)
				controllerClass = require @config.controllers[controller]
				@controllers[controller] = new controllerClass @server

		catch e
			@server.logger.error "Невозможно загрузить конфиги - #{e.message}"
			process.exit -1		

	parseCommand: (client, command) ->
		cmd = ""
		try
			cmd = JSON.parse(command)
		catch exception
			@server.logger.error "Некорректный JSON в команде #{command}"
			return utils.generateAnswer "Ошибка", "Некорректный JSON в команде #{command}"
		
		return utils.generateAnswer "Ошибка", "Нет полей type, data" unless cmd.data && cmd.type

		try
			@executeCommand(client, cmd)
		catch exception
			@server.logger.error exception
			return utils.generateAnswer "Ошибка", "Неизвестная ошибка на сервере"

	executeCommand: (client, command) ->
		route = @config[command.type]
		return  utils.generateAnswer "Ошибка", 'Незвестная команда' if route == undefined

		answer = @controllers[route.controller][route.method](client, command.data)
		unless answer == null
			answer = utils.generateAnswer command.type, answer 
			@server.logger.info "Отправляю юзеру #{client.id} - #{answer}"
			return answer

module.exports = Router