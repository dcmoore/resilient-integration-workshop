# Tenkai Suru API

[![Build Status](https://travis-ci.org/workshop/workshop-api.svg)](https://travis-ci.org/workshop/workshop-api)

API that enables deployments of any kind

## Set Up

Install [Leiningen 2](https://github.com/technomancy/leiningen) if you already haven't. Make sure you have the lein command available in your terminal with `which lein`. Then download the source code with [Git](http://git-scm.com/downloads) by cloning the repository. Now cd into the project's directory.

_make sure to avoid leiningen versions between 2.4.3 and 2.5.0_

### Setting up the database

```bash
createdb workshop-api-dev
lein with-profile development task migrate
```

### Running the app

Then start the server with the following command:

```bash
lein with-profile development run
```

-OR-

```bash
lein with-profile development uberjar
java -jar target/workshop-api-0.1.0-standalone.jar
```

Once it started, you can hit the server on port 4000.

```bash
http://localhost:4000/
```

When running the project you can set the CONFIG_HOME environment variable to tell the project to read configuration files from a different directory.

### Certificate
If you have never setup lein before, you may get this error when you try to run the application

```bash
Could not transfer artifact xxxx from/to clojars (https://clojars.org/repo/): peer not authenticated
```

If you come across that problem, download the certificate.

```bash
echo -n | openssl s_client -connect clojars.org:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > /tmp/clojars.cert
```

Then move the certificate to your Java certs. For Java 7/8

```bash
mv /tmp/clojars.cert /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/security/
```

## Running Test
You can run the tests with the following command:

```bash
lein spec
```
Or if you want to run the test automatically when a file is change, run spec with -a
```bash
lein spec -a
```
