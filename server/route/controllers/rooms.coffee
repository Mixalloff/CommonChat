Room = require '../../models/room'

class Rooms
	constructor: (@server) ->

	createChat: (client, params) =>
		participants = params.participants.map (clientID) =>
			@server.getClientById(clientID)
		room = new Room participants
		@server.rooms.push room
		return "Комната #{room.id} создана"

	enterRoom: (client, params) ->
		room = @server.getRoomByID(params.id)
		room.addParticipant client unless room == null

	getRooms: (client, params) ->
		do @server.getRooms

	showParticipants: (client, params) ->
		do client.room.showParticipants unless client.room == null

module.exports = Rooms