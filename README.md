# Test Automation Agent

## Overview

This repository contains a Maven multi-module project that demonstrates an autonomous
visual testing agent. The agent executes test scenarios by iteratively:

1. Taking a screenshot of the target web page.
2. Sending the screenshot and scenario context to a large language model (LLM).
3. Executing tool calls returned by the LLM (clicks or finishing the test).

The project is built with **Java 21** and **Spring Boot** and relies on
[Playwright](https://playwright.dev/) for browser automation and the
OpenAI API for reasoning about screenshots.

## Modules and Key Components

### `test` module

Contains the application code and an integration test.

- **Agent** – orchestrates the conversation with the LLM and the execution of
  [MCP](https://github.com/modelcontextprotocol) tools.
- **McpGateway** – looks up and invokes registered MCP tools.
- **Tools / PlaywrightTools** – tool interface and Playwright implementation
  providing `click_xy` and `screenshot` functions.
- **LLMClient / GPTClient** – abstraction and OpenAI implementation for asking
  the model what to do next.
- **Configuration** – Spring configuration classes for Playwright, MCP and LLM
  wiring.
- **AgentTest** – example integration test that verifies a simple scenario.

## Configuration

The agent reads settings from property files in `test/src/main/resources`.

### OpenAI

Fill in `openai.properties` with your credentials and options:

```properties
openai.key=sk-your-key
openai.max-completion-tokens=1024
openai.instructions-path=classpath:instruction.txt
```

The `openai.instructions-path` points to a text file with system instructions
that guide the model during the test run.

### Playwright

Specify the target web application in `playwright.properties`:

```properties
playwright.app-host=https://example.org
```

Playwright launches a Chromium browser and navigates to this URL before the
scenario begins.

### Customising the Scenario

Edit `instruction.txt` to describe the steps the agent should take. The file is
referenced by `openai.instructions-path` and can include detailed guidance and
expected outcomes.

## Running

After configuring the properties, build and run from the repository root.
Ensure JDK 21 and Maven 3.9+ are installed and that the OpenAI key grants
network access.

### Run the integration test

```bash
mvn -pl test test
```

The test launches the agent, which interacts with the browser until it calls the
`finish` tool.

### Run the application manually

To experiment interactively, start the Spring Boot application:

```bash
mvn -pl test spring-boot:run
```

The application listens on `http://localhost:8080` and executes the configured
scenario.

## Requirements

- JDK 21
- Maven 3.9+
- OpenAI API key
- Internet access for the tested application

## License

This project is provided for demonstration purposes without warranty.
