curl "http://localhost:8080/topjava_war_exploded/rest/user/meals"
curl "http://localhost:8080/topjava_war_exploded/rest/user/meals/100002"
curl "http://localhost:8080/topjava_war_exploded/rest/user/meals/filter?startDate=2020-01-30&startTime=09:00:00&endDate=2020-01-30&endTime=18:00:00"
curl "http://localhost:8080/topjava_war_exploded/rest/user/meals/filter?startDate=null&startTime=null&endDate=null&endTime=null"
curl "http://localhost:8080/topjava_war_exploded/rest/user/meals/filter?startDate=Yesterday&startTime=09:00:67&endDate=pikachu&endTime=null"
curl -X DELETE "http://localhost:8080/topjava_war_exploded//rest/user/meals/100002"
curl -X PUT -H "Content-Type: application/json" -d "{\"id\":100002,\"dateTime\":\"2020-01-30T10:00:00\",\"description\":\"New breakfast\",\"calories\":200, \"user\":null}" http://localhost:8080/topjava_war_exploded/rest/user/meals/100002
curl -X POST -H "Content-Type: application/json" -d "{\"id\":null,\"dateTime\":\"2020-01-30T17:10:00\",\"description\":\"Carolina Reaper\",\"calories\":400, \"user\":null}"  http://localhost:8080/topjava_war_exploded/rest/user/meals