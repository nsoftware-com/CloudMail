/*
 * Cloud Mail 2024 Java Edition - Sample Project
 *
 * This sample project demonstrates the usage of Cloud Mail in a 
 * simple, straightforward way. It is not intended to be a complete 
 * application. Error handling and other checks are simplified for clarity.
 *
 * www.nsoftware.com/cloudmail
 *
 * This code is subject to the terms and conditions specified in the 
 * corresponding product license agreement which outlines the authorized 
 * usage and restrictions.
 */

import java.io.*;
import cloudmail.*;
import java.io.*;

public class amazonses extends ConsoleDemo {
  public static void main(String[] args) {
    AmazonSES amazonses = new AmazonSES();
    try {
	  amazonses.setAccessKey(prompt("AccessKey"));
      amazonses.setSecretKey(prompt("SecretKey"));
      
	  amazonses.setFrom(prompt("From"));
      amazonses.setSendTo(prompt("To"));
      amazonses.setSubject(prompt("Subject"));

      System.out.println("Enter the message. To end the message, enter \".\" on a single line by itself.");
      System.out.println("Message:");
      String message = "";

      String buffer = input();
      while (buffer.compareTo(".") != 0) {
        message += buffer + "\r\n";
        buffer = input();
      }

      amazonses.setMessageHTML(message);

      //if you want to add attachment:
      System.out.println("Enter file names to attach, or press enter to skip...");
      buffer = prompt("  File Name");
      while (buffer.length() > 0) {
        amazonses.addAttachment(buffer);
        buffer = prompt("  File Name");
      }

      System.out.println("Sending message ...");
      amazonses.send();

      System.out.println("Message sent successfully");
      System.exit(0);
    } catch (CloudMailException ipwe) {
      System.out.println(ipwe.getMessage());
      System.exit(ipwe.getCode());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}




class ConsoleDemo {
  private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

  static String input() {
    try {
      return bf.readLine();
    } catch (IOException ioe) {
      return "";
    }
  }
  static char read() {
    return input().charAt(0);
  }

  static String prompt(String label) {
    return prompt(label, ":");
  }
  static String prompt(String label, String punctuation) {
    System.out.print(label + punctuation + " ");
    return input();
  }
  static String prompt(String label, String punctuation, String defaultVal) {
      System.out.print(label + " [" + defaultVal + "]" + punctuation + " ");
      String response = input();
      if (response.equals(""))
        return defaultVal;
      else
        return response;
  }

  static char ask(String label) {
    return ask(label, "?");
  }
  static char ask(String label, String punctuation) {
    return ask(label, punctuation, "(y/n)");
  }
  static char ask(String label, String punctuation, String answers) {
    System.out.print(label + punctuation + " " + answers + " ");
    return Character.toLowerCase(read());
  }

  static void displayError(Exception e) {
    System.out.print("Error");
    if (e instanceof CloudMailException) {
      System.out.print(" (" + ((CloudMailException) e).getCode() + ")");
    }
    System.out.println(": " + e.getMessage());
    e.printStackTrace();
  }

  /**
   * Takes a list of switch arguments or name-value arguments and turns it into a map.
   */
  static java.util.Map<String, String> parseArgs(String[] args) {
    java.util.Map<String, String> map = new java.util.HashMap<String, String>();
    
    for (int i = 0; i < args.length; i++) {
      // Add a key to the map for each argument.
      if (args[i].startsWith("-")) {
        // If the next argument does NOT start with a "-" then it is a value.
        if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
          // Save the value and skip the next entry in the list of arguments.
          map.put(args[i].toLowerCase().replaceFirst("^-+", ""), args[i + 1]);
          i++;
        } else {
          // If the next argument starts with a "-", then we assume the current one is a switch.
          map.put(args[i].toLowerCase().replaceFirst("^-+", ""), "");
        }
      } else {
        // If the argument does not start with a "-", store the argument based on the index.
        map.put(Integer.toString(i), args[i].toLowerCase());
      }
    }
    return map;
  }
}



