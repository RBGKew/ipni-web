# IPNI Web

# Building

The project as a whole can be built using:

```mvn install -Ddocker.registry=ipni/ -Dmaven.test.skip=true```

This will build the java code, frontend via webpack, and docker images.

To skip the webpack build, run:

```mvn install -Ddocker.registry=ipni/ -Dmaven.test.skip=true -Dskip.npm```

# Development

When developing the frontend, it is convenient to use the webpack development server for
automatic rebuilds etc. You can run this by going into the `web/src/main/resources/`
folder and running

```npm start```

this will start a dev server running on port `8080`
