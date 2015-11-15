class Messages
	constructor: (@server) ->

	sendMessage: (client, data) ->
		client.sendMessageToRoom(data)
		return null


module.exports = Messages