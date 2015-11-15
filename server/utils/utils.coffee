class Utils
	constructor: ->

	generateAnswer: (type, data) ->
		JSON.stringify {
			type: type,
			data: data
		}

module.exports = new Utils