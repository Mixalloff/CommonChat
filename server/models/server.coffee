Client 	       = require './client'
WebSocket = new require 'ws'
Router         = require '../route/router'
Logger          = require '../utils/logger'
Room           = require './room'
fs 	       = require 'fs'

class Server
	constructor: ->
		@configs = JSON.parse fs.readFileSync 'configs/server.json'
		@clients  = []
		@rooms  = []
		@logger   = new Logger @configs.loggerLevel
		@router   = new Router  @

	start: (port, host) ->
		@webSocketServer = new WebSocket.Server {
			port: port,
			host: host
		}

		@logger.info "Сервер запущен на #{host}, порт - #{port}"

		@webSocketServer.on 'connection', (ws) =>
			id = Math.random()
			client = new Client id, ws
			@clients.push client

			@logger.info "Подключился новый клиент с ID = #{id}"

			ws.on 'close', =>
				do client.setOffline
				@logger.info "Отсоединился клиент с ID = #{client.id}"

			ws.on 'message', (msg) =>
				@logger.info "Получена команда '#{msg}' от #{client.id}"
				ws.send @router.parseCommand client, msg

	getClientByWs: (ws) ->
		for client in @clients
			return client if client.ws == ws
		return null

	getClientById: (id) ->
		for client in @clients
			return client if client.id == id
		return null

	getRooms: ->
		@rooms.map (room) ->
			room.id

	getRoomByID: (id) ->
		for room in @rooms
			return room if room.id == id
		return null

	activeClients: ->
		cl = []

		for client in @clients
			cl.push client if client.online

	clientsInSearch: ->
		results = []
		results.push client for client in @clients when client.isInSearch()
		results

	isEmpty: ->
		@clients.length == 0

module.exports = new Server