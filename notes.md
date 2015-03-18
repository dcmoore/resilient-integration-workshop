Resilient Integrations Workshop
===============================



Problems of software
====================
mis-match between documentation and actual API
  - lesson: Never trust documentation
schema of response to change based on request (ie. missing keys)
  - lesson: Avoid null pointer exceptions
JSON key values to either be array or single element
  - lesson: You sometimes have to normalize the response yourself
release an update of the API that breaks the original contract
  - lesson: Avoid leaking the 3rd Party's interface throughout your codebase
have JSON response have a header that specifies it is text/html
  - lesson:
service takes a long time to respond (every 15th request sleep for 2 minutes)
service is non-responsive




total error case is completely different schema:

{
  timestamp: 1424905888323
  error: "Internal Server Error"
  status: 500
  exception: "java.lang.RuntimeException"
  message: "org.codehaus.jackson.map.JsonMappingException: (was java.lang.NullPointerException) (through reference chain: com.ticketmaster.api.models.Refundability["order"]->com.ticketmaster.api.models.Order["eventId"])"
}






Structure
=========
Introduction
Introduce the application they will integrate with
Introduce them on how to edit the partially completed client
Turn them loose
Go through presentation
Do a group retro




Misc Notes
==========
If database is unreachable, does the connecting webserver have to be unreachable too?
  - use timeouts
Circuit Breaker
  - don't wait for timeouts all the time























/excavate

bucket-id
elements
  gold
    units
    purity
  dirt
    units


/store?userId=1&bucketId=1

sucess


/totals

userId
goldUnits









curl -X POST "localhost:4000/v1/register?userName=TestUser"
curl -X POST "localhost:4000/v1/excavate?userId=21137c38-d176-4574-969c-087ec21f31c8"
curl -X POST "localhost:4000/v1/store?userId=21137c38-d176-4574-969c-087ec21f31c8&bucketId=e3ae946b-98a6-4484-8cd1-f669d7d73c76"
curl -X GET "localhost:4000totals?userId=21137c38-d176-4574-969c-087ec21f31c8"
