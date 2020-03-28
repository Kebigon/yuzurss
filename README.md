# YuzuRSS
Feed aggregator microservice based on Spring

## Usage

Just send a POST request, with in the body of the request a JSON object with the below:

| Parameter  | Description                                                                                                                                 |
| ---------- | ------------------------------------------------------------------------------------------------------------------------------------------- |
| limit      | Number of feed entries to be provided                                                                                                       |
| urls       | List of RSS/Atom/RDF feed URLs to aggregate                                                                                                 |
| dateBefore | Filter the entries to only keep the ones with a publication date before or equals to this argument. Format is "yyyy-MM-dd", timezone is UTC |
| dateAfter  | Filter the entries to only keep the ones with a publication date after or equals to this argument. Format is "yyyy-MM-dd", timezone is UTC  |

Example:

	POST https://yuzurss.kebigon.xyz

	{
		"limit": 2,
		"urls": [
			"https://www.youtube.com/feeds/videos.xml?user=epenser1",
			"https://www.youtube.com/feeds/videos.xml?user=scilabus",
			"https://www.youtube.com/feeds/videos.xml?user=TroncheEnBiais"
		],
		"dateAfter": "2020-01-22"
	}

The response will be a [JSON Feed](https://jsonfeed.org/version/1):

Example:

	{
		"version": "https://jsonfeed.org/version/1",
		"title": "YuzuRSS aggregated feed",
		"items": [
			{
				"id": "yt:video:C9bLMf6Q-qk",
				"url": "https://invidio.us/watch?v=C9bLMf6Q-qk",
				"title": "Pour en finir avec l'homéopathie - Tronche de Fake 4.8",
				"author": {
					"name": "La Tronche en Biais"
				},
				"date_published": "2020-01-23T10:43:08.000Z"
			},
			{
				"id": "yt:video:hJe5MDMWOaU",
				"url": "https://invidio.us/watch?v=hJe5MDMWOaU",
				"title": "Les trous noirs (1/2) - 48 - e-penser",
				"author": {
					"name": "e-penser"
				},
				"date_published": "2020-01-23T08:09:43.000Z"
			}
		]
	}
