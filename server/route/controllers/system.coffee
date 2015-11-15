class System
	constructor: (@server) ->

	activeUsers: ->
		@server.clients.map (client) ->
			return client.id


module.exports = System