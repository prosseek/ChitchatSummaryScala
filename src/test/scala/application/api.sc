import api.API

/**
  * Created by smcho on 4/16/16.
  */

val summary =
  """
    |{
    |  "string": "James",
    |  "age": 10,
    |  "date": [10, 3, 17],
    |  "time": [12, 14],
    |  "latitude": [1, 2, 3, 4],
    |  "longitude": [11, 12, 13, 14]
    |}
    |""".stripMargin

val f = API.create_fbf_summary(jsonSummary = summary, Q = 2)
println(f.get("string").get)
println(f.get("latitude").get)
println(f.get("longitude").get)

val res = API.serialize(f)
println(res.mkString(":"))
API.save(f, "/Users/smcho/github/ChitchatSummaryScala/target/summary.bin")