# Resilient Integration Workshop

This is an API that is intentionally difficult to integrate with. I use this in conjunction with a talk to introduce ways to build more resilient integration points.

## Running the app

```bash
lein with-profile development trampoline run $PORT
```

-or-

```bash
docker build -t workshop .
docker run -p 8085:8085 workshop
```
