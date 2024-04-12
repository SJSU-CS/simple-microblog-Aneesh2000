package edu.sjsu.cmpe272.simpleblog.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@SpringBootApplication
@Command
public class ClientApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(ClientApplication.class, args)));
    }
}
