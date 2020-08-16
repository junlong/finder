# finder
a toy app to find and operate files.
## compile
```shell
mvn packge
```
## usage
### basic
```shell
java -jar finder-1.0.0-jar-with-dependencies.jar <options>
```
### example
```shell
java -jar finder-1.0.0-jar-with-dependencies.jar -p /home/logs -v
```

### options
```shell script
usage: finder -p <path> -v
 -c,--copy <dest>     dest is short for destination path. copy will be
                      skipped when target file is already exist in
                      destination.
 -d,--delete          delete file
 -m,--move <dest>     dest is short for destination path. move will be
                      skipped when target file is already exist in
                      destination.
 -p,--path <path>     path is short for source path.
 -r,--regex <regex>   regex of file name
 -v,--verbose         show execute details

```
