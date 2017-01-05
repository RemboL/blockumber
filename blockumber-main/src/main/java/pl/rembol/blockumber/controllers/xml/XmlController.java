package pl.rembol.blockumber.controllers.xml;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtworks.xstream.XStream;
import gherkin.parser.ParseError;
import gherkin.parser.Parser;
import pl.rembol.blockumber.stepdefs.BlockDefinitionsService;

@RestController
@RequestMapping("/blockumber/xml")
class XmlController {

    private XStream xstream;

    private final BlockDefinitionsService blockDefinitionsService;
    
    @Autowired
    XmlController(BlockDefinitionsService blockDefinitionsService) {
        this.blockDefinitionsService = blockDefinitionsService;
        xstream = new XStream();
        xstream.processAnnotations(Xml.class);
        xstream.processAnnotations(Block.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "fromFeature")
    ResponseEntity<String> featureToXml(@RequestBody String feature) throws UnsupportedEncodingException {
        feature = URLDecoder.decode(feature, "UTF-8");

        Xml xml = new Xml();
        try {
            new Parser(new BlockBuilder(blockDefinitionsService.getStepDefs(), xml)).parse(feature, "", 0);

        } catch (ParseError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String string = xstream.toXML(xml);
        return ResponseEntity.ok(string);
    }

}
