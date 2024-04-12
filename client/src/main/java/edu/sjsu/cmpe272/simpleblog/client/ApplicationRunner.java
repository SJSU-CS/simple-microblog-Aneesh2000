package edu.sjsu.cmpe272.simpleblog.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Slf4j
@Component
public class ApplicationRunner implements CommandLineRunner, ExitCodeGenerator{
    private final CommandLineInterface cli;
    private int exitCode;


    public ApplicationRunner(CommandLineInterface cli) {
        this.cli = cli;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Running commands through cli - "+ cli.toString());
        exitCode = new CommandLine(cli).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
