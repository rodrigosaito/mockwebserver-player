package com.rodrigosaito.mockwebserver.player;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

public class TapeReader {

    public Tape read(final String tapeName) {
        String path = "/plays/" + tapeName + ".yaml";

        InputStream input = getClass().getResourceAsStream(path);

        if (input == null) {
            throw new TapeNotFoundException("Could not find tape: " + path);
        }

        Object obj = getYaml().load(input);

        return (Tape) obj;
    }

    private Yaml getYaml() {
        Representer representer = new Representer();
        representer.addClassTag(Tape.class, new Tag("!play"));

        Constructor constructor = new Constructor();
        constructor.addTypeDescription(new TypeDescription(Tape.class, "!play"));

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setWidth(256);

        Yaml yaml = new Yaml(constructor, representer, dumperOptions);

        return yaml;
    }

}
