Utils = require('../utils/utils')

class Room
	constructor: (@participants) ->
		@id = do Math.random
		for client in @participants
			client.enterRoom @

	sendMessage: (from, msg) ->
		client.sendMessage(Utils.generateAnswer 'message', {"message":msg, "sender":from.id} ) for client in @participants when client != from && client.online

	showParticipants: ->
		@participants.map (client) ->
			{ 
				id: client.id,
				isOnline: client.online
			}

	clientExit: (client) ->

class PublicRoom extends Room
	addParticipant: (client) ->
		@participants.push client
		client.enterRoom @
		@sendMessage client, "Подключился ID = #{client.id}"

module.exports = PublicRoom