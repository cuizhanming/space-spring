
## Useful Commands

### Are there newer versions available?
```
# Maven
mvn versions:display-dependency-updates

# Gradle
plugins {
  id "com.github.ben-manes.versions" version "0.42.0"
}

gradle dependencyUpdates -Drevision=release
```

### Is the package still in use?
```
# Maven
mvn dependency:analyze -DignoreNonCompile

# Gradle
plugins {
   id "nebula.lint" version "17.7.0"
}

gradle lintGradle -PgradleLint.rules=unused-dependency
```