package com.monopoly.game.gameCore;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.ini4j.Profile;
import org.ini4j.Wini;

public class ReadIni {
  Wini ini;
  public ReadIni() throws IOException {
    ini = new Wini(new File("./config.ini"));
  }

  public Profile.Section getSection(String name) {
    return (Profile.Section) ini.get(name);
  }

  public Collection<Profile.Section> listOfSection() {
    return ini.values();
  }

  public int getNumValue(String sectionName, String name) {
    Profile.Section section = getSection(sectionName);
    return Integer.parseInt(section.get(name).toString());
  }

  public String getStrValue(String sectionName, String name) {
    Profile.Section section = getSection(sectionName);
    return section.get(name).toString();
  }

  public int fetchNumVal(String sectionName, String name) {
    Profile.Section section = getSection(sectionName);
    return Integer.parseInt(section.fetch(name).toString());
  }
}
