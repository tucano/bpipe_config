# bpipe-config

Bpipe configuration and reporting.




# DEVELOPMENT NOTES

* Gradle templates: https://github.com/townsfolk/gradle-templates
* JANSI tutorial: http://jameswilliams.be/blog/entry/240


### TEST JAR

* test java compiled run with:

```
java  -cp groovy-all:build/libs/jarfile bpipeconfig.BpipeConfig
```

Example:

```
java  -cp /usr/local/groovy/embeddable/groovy-all-2.1.7.jar:build/libs/bpipe_config.jar bpipeconfig.BpipeConfig
```

### TEST BASH SCRIPT

From devel root dir:

```
export BPIPE_CONFIG_HOME=./build/stage/bpipeconfig-0.1 && ./build/stage/bpipeconfig-0.1/bin/bpipe-config
```

From data dir:

```
export BPIPE_CONFIG_HOME=../build/stage/bpipeconfig-0.1 && ../build/stage/bpipeconfig-0.1/bin/bpipe-config
```

### SUBLIME

* sublime-gradle: https://github.com/koizuss/sublime-gradle

* JAVADOC SNIPPETS: https://github.com/ekryski/sublime-comment-snippets

#### Snippets

* `todo` - TODO
* `fix` - FIXME
* `note` - NOTE
* `/**` - Nicely formatted block comment
* `/==` - Nice divider comment
* `jdoc:c` - Java Doc style class comment
* `jdoc:m` - Java Doc style method comment
