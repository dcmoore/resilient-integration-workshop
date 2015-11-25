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









curl -X POST "https://resilient-integration-workshop.herokuapp.com/v1/register?userName=TestUser"
curl -X POST "https://resilient-integration-workshop.herokuapp.com/v1/excavate"
curl -X POST "https://resilient-integration-workshop.herokuapp.com/v1/store?userId=XXXXX&bucketId=XXXXX"
curl -X GET "https://resilient-integration-workshop.herokuapp.com/v1/totals?userId=XXXXX"


curl -X POST "localhost:4000/v1/register?userName=TestUser"
curl -X POST "localhost:4000/v1/excavate"
curl -X POST "localhost:4000/v1/store?userId=XXXXX&bucketId=XXXXX"
curl -X GET "localhost:4000/v1/totals?userId=XXXXX"



































New talk



Building Resilient Integrations



Failure is everywhere
  - Hardware
  - Software
  - User Error

"Switches go down, garbage collection pauses make masters “disappear”, socket writes seem to succeed but have actually failed on the other machine, a slow disk drive on one machines causes a communication protocol in the whole cluster to crawl, and so on. Reading from local memory is simply more stable than reading across a few switches.

Design for failure." --Jeff Hodges

Client Retries
  - Back off strategies to not DDOS your API
  - Make your API idempotent so retries can suceed
  - Circuit Breaker pattern
    - https://github.com/apache/zookeeper

"Try to avoid complete reliance on any single system, even if it is a highly reliable distributed system"
  - You always want a Plan B

"Realtime Configuration"
  - "Feature files"
  - "Rate limiting"

"Replication and failover are the key ingredients for building highly resilient storage and caching layers"

Latency in distributed systems is problematic. One slow response from a non-critical API could slow the entire request down. Especially if there are dependent requests.

System overload can cause failure

"Coordination is very hard" --Jeff Hodges




Misc
  - https://www.typesafe.com/blog/reactive-manifesto-20
  - https://queue.acm.org/detail.cfm?id=2353017
  - http://www.somethingsimilar.com/2013/01/14/notes-on-distributed-systems-for-young-bloods
