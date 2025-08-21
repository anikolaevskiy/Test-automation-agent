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

### `core`

Domain model and shared infrastructure.

- **Agent** – orchestrates the conversation with the LLM and the execution of
  [MCP](https://github.com/modelcontextprotocol) tools.
- **Action** – describes a tool invocation requested by the model.
- **Tools / McpGateway** – tool interface and gateway for invoking tool methods.
- **AgentProperties / CommonConfiguration** – common configuration and settings.
- **Tool Schemas** – records describing tool parameters such as `ClickXY` and
  `Finish`.

### `llm-openai`

OpenAI-specific client and configuration.

- **GPTClient** – OpenAI implementation of the language model client.
- **OpenAIConfiguration / OpenAIProperties** – configure API access and default
  chat parameters.
- **Resources** – `openai.properties` and `instruction.txt`.

### `tools-playwright`

Playwright-based MCP tools and browser configuration.

- **PlaywrightTools** – implementation of `Tools` backed by Playwright.
- **PlaywrightConfiguration / PlaywrightProperties** – create and configure the
  browser.
- **Resources** – `playwright.properties`.

### `app`

Application wiring and tests.

- **AgentConfiguration / MainConfiguration** – assemble the agent for tests.
- **AgentTest** – example integration test that verifies a simple scenario.

## Architecture and Extensibility

The agent deliberately decouples decision making from environment interaction.
The `LLMClient` analyses screenshots and decides which tool to call next, while
`Tools` implementations execute the request against the target application.
Because these pieces communicate only through the `McpGateway`, either side can
be replaced or extended without touching the other.

### Adding a new tool

To expose an additional action to the agent:

1. **Describe the tool parameters.** Create a record under
   `core/src/main/java/com/test/example/agent/llm/tools`. Each field becomes a
   model argument.
2. **Declare the tool contract.** Add a method to the
   `com.test.example.mcp.tools.Tools` interface and annotate it with `@Tool`.
   The method should return a `Tools.Result`.
3. **Implement the behaviour.** Extend an existing implementation such as
    `tools-playwright` or provide your own class and implement the method.
4. **Register the tool with the LLM.** In
    `llm-openai/src/main/java/com/test/example/configuration/openai/OpenAIConfiguration`
   add `addTool(YourTool.class)` to the `ChatCompletionCreateParams` builder so
   the model knows about it.
5. **Expose the implementation.** Ensure your `Tools` implementation is
   registered as a bean in
   `core/src/main/java/com/test/example/configuration/mcp/McpConfiguration` so
   the gateway can discover it.

After these steps the agent will automatically consider the new action during a
test run. No changes to the core orchestration logic are required.

## Configuration

The agent reads settings from module-specific property files:

- `core/src/main/resources/agent.properties` – general agent settings.
- `llm-openai/src/main/resources/openai.properties` – OpenAI credentials and
  instruction file path.
- `tools-playwright/src/main/resources/playwright.properties` – Playwright
  options and target application.

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
mvn -pl app test
```

The test launches the agent, which interacts with the browser until it calls the
`finish` tool.

### Run the application manually

To experiment interactively, start the Spring Boot application:

```bash
mvn -pl app spring-boot:run
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
