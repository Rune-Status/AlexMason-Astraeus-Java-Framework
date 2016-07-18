package astraeus.game.io;

import astraeus.game.model.World;
import astraeus.util.TextFileParser;

import java.io.IOException;
import java.util.Scanner;

public class UUIDBanParser extends TextFileParser {

    public UUIDBanParser() {
        super("./Data/punishment/uuid_bans");
    }

    @Override
    public void parse(Scanner reader) throws IOException {
        String uuid = reader.nextLine();
        World.WORLD.getBannedUUIDs().add(uuid);
    }

}