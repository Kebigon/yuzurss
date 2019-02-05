# YuzuRSS
RSS aggregator microservice based on Spring Webflux

## Usage

Just send a POST request to /feed/, with in the body of the request a JSON object with the below:

| Parameter | Description                                 |
| --------- | ------------------------------------------- |
| limit     | Number of feed entries to be provided       |
| urls      | List of RSS/Atom/RDF feed URLs to aggregate |

Example:

	POST http://5.39.83.109:8091/feed/

	{
		"limit": 20,
		"urls": [
			"https://www.youtube.com/feeds/videos.xml?user=epenser1",
			"https://www.youtube.com/feeds/videos.xml?user=scilabus",
			"https://www.youtube.com/feeds/videos.xml?user=TroncheEnBiais"
		]
	}

The response will be a JSON list of entry, with the below:

| Parameter | Description                                                            |
| --------- | ---------------------------------------------------------------------- |
| title     | Title of the entry                                                     |
| link      | Link to open the content of the entry                                  |
| published | Time the entry has been published, format 2019-01-27T09:40:02.000+0000 |
| author    | Name of the author of the entry                                        |

Example:

	[
		{
			"title": "\"Métaphysique et Zététique\" Entretien Sceptique avec Sardoche",
			"link": "https://www.youtube.com/watch?v=WxNQp0fDFg0",
			"published": "2019-01-27T09:40:02.000+0000",
			"author": "La Tronche en Biais"
		},
		{
			"title": "#06 L'odorat, partie 2 - Les sens humains - e-penser",
			"link": "https://www.youtube.com/watch?v=VQjutS-MBUc",
			"published": "2019-01-20T14:05:57.000+0000",
			"author": "e-penser"
		}
	]
