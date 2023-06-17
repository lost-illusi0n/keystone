# Keystone
[![Version](https://img.shields.io/maven-central/v/dev.sitar/keystone)](https://search.maven.org/artifact/dev.sitar/keystone)

A no-dependency Kotlin multiplatform library for pipelines.

## Dependency
Keystone releases are published to Maven Central (soon). Replace `{version}` with the latest version of `keystone`.
```kotlin
implementation("dev.sitar:keystone:{version}")
```


# What
On occasion, developers appreciate a simple pipeline api; often in different projects. You can write/copy-and paste the same pipeline API in all your projects. You can also depend on one of the few libraries that expose a pipeline API, along with the numerous dependencies and unused code that comes with them. Keystone is simply a pipeline library, with no additional dependencies, that you can depend on.