# How to contribute

We would greatly value feedback and contributions from our community.

## Running tests

To run tests:

- Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)

- Run `make test`. This will start a docker compose stack (including a [zamzar-mock](https://github.com/zamzar/zamzar-mock)
  container) and run the tests against it.

## Propagating changes to the API spec

The Zamzar API has an [OpenAPI specification](https://github.com/zamzar/zamzar-spec). Changes to the spec can be
propagated to this library by running `make sync generate`. This will download the latest version of the spec, start a
docker compose stack (including an [OpenAPI Generator](https://openapi-generator.tech) container) and regenerate the
client code.

## Releasing a new version

PRs to this repository should be made against the `main` branch and labelled with:

* `bump:patch` for bug fixes
* `bump:minor` for new features
* `bump:major` for breaking changes

When a PR is merged, the CI will automatically publish a new version to Maven Central.
