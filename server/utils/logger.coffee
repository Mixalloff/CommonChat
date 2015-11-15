###
Уровень ошибок:
3 - info
2 - warn
1 - error
###

class Logger
	constructor: (@loggerLevel) ->

	info: (message) ->
		console.log "#{new Date()} --- #{message.replace('\n', '')}" if @loggerLevel >= 3
	warn: (message) ->
		console.log "#{new Date()} --- ПРЕДУПРЕЖДЕНИЕ! #{message.replace('\n', '')}" if @loggerLevel >= 2
	error: (message) ->
		if message instanceof Error
			console.log "#{new Date()} --- ОШИБКА! #{message} \n\n #{message.stack}"
		else
			console.log "#{new Date()} --- ОШИБКА! #{message.replace('\n', '')}"

module.exports = Logger