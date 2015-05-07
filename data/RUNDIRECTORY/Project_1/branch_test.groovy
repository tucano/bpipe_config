 // USE JSON INPUT FILE
import groovy.json.JsonSlurper
branches = new JsonSlurper().parseText(new File(args[0]).text)

hello =
{

}

run {
  branches * [hello]
}
