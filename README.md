# Test Automation Agent

An experimental autonomous testing agent that uses a Large Language Model (LLM) to drive a browser through [Model Context Protocol](https://github.com/modelcontextprotocol) (MCP) tools. The project demonstrates how screenshot analysis and tool execution can be combined to validate a user scenario end‑to‑end.

## How it works

1. **Screenshot** – the agent captures the current browser view using a Playwright powered MCP tool.
2. **Reason** – the screenshot and test context are sent to the LLM which decides which tool to invoke next.
3. **Act** – the chosen tool is executed (e.g. `click_xy` or `finish`) and the loop continues until the model stops the scenario or the action limit is reached.

The decision making lives entirely in the LLM while all side effects are performed by MCP tools. This separation keeps the orchestration simple and allows each side to evolve independently.

## Configuration

All configuration lives in `test/src/test/resources/application.properties`:

```properties
# API key used for authenticating with OpenAI services
openai.key=sk-your-key
```

Every property in the file is documented in‑line to clarify its purpose. The most important groups are:

- **Spring** – active profiles and server settings.
- **Agent** – limits and delays protecting against runaway test runs.
- **OpenAI** – credentials and model options.
- **Playwright** – target URL and browser viewport.

## Extending the agent

### Adding a new tool

1. **Describe the tool arguments.** Create a record under `core/src/main/java/com/test/example/llm/tools` and annotate the fields with JSON descriptions.
2. **Expose the action to the LLM.** Register the record in `OpenAIConfiguration#params` using `.addTool(YourTool.class)`.
3. **Implement the behaviour.** Add a method to `McpTools` and implement it in `PlaywrightMcpTools` or a custom class.

### Plugging a different LLM

Implement `LlmClient` and provide a Spring configuration that exposes it as a bean. Existing agent code will pick it up automatically.

## Running

Build the project with Maven 3.9+ and JDK 21:

```bash
mvn -pl test test
```

The `QAgentTest` integration test demonstrates a complete run, attaching intermediate screenshots to an Allure report.

To experiment manually start the Spring Boot application:

```bash
mvn -pl test spring-boot:run
```

## License

This project is released under the [Creative Commons Attribution‑NonCommercial 4.0 International](https://creativecommons.org/licenses/by-nc/4.0/) license. It is intended for evaluation and educational use only and may **not** be used for commercial purposes without prior permission.

If you are interested in contributing or developing the idea further, please contact me on [LinkedIn](https://www.linkedin.com/in/nikolaevskiy).
