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

## Running the Tests

The tests require a valid OpenAI API key and access to the target web
application. Provide configuration via `test/src/main/resources/openai.properties`
and `playwright.properties`.

```bash
mvn test
```

The test launches a Playwright-controlled Chromium instance, navigates to the
configured URL and lets the agent drive the scenario.

## Requirements

- JDK 21
- Maven 3.9+
- OpenAI API key
- Internet access for the tested application

## License

This project is provided for demonstration purposes without warranty.

