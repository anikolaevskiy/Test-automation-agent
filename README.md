# Test Automation Agent

An experimental autonomous testing agent that uses a Large Language Model (LLM) to drive applications through [Model Context Protocol](https://github.com/modelcontextprotocol) (MCP) tools. The repository ships with a Playwright‑based implementation for web browsers but the architecture allows plugging in other adapters, for example an Appium MCP for mobile apps.

## How it works

1. **Observe** – the agent captures the current application view through an MCP tool. In this sample the Playwright MCP returns web page screenshots.
2. **Reason** – the screenshot and test context are sent to the LLM which decides which tool to invoke next.
3. **Act** – the chosen tool is executed (e.g. `click_xy` or `finish`) and the loop continues until the model stops the scenario or the action limit is reached.

The decision making lives entirely in the LLM while all side effects are performed by MCP tools. This separation keeps the orchestration simple and allows the automation layer (Playwright, Appium, etc.) to evolve independently from the agent core.

## Project structure

The repository is split into modules so individual pieces can evolve separately:

- **core** – agent engine and shared configuration.
- **llm** – adapters for LLM providers. Currently contains **llm-openai** with the OpenAI client.
- **mcp** – MCP gateways and tools for automation frameworks. The sample **mcp-browser** module exposes Playwright actions.
- **test** – integration tests, Allure helpers and a stub test management client demonstrating end‑to‑end usage.

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
- **Automation** – settings for the chosen MCP adapter (Playwright URL and viewport in this sample).

## Extending the agent

### Adding a new tool

1. **Describe the tool arguments.** Create a record under `core/src/main/java/com/test/example/llm/tools` and annotate the fields with JSON descriptions.
2. **Expose the action to the LLM.** Register the record in `OpenAIConfiguration#params` using `.addTool(YourTool.class)`.
3. **Implement the behaviour.** Add a method to `McpTools` and implement it in `PlaywrightMcpTools` or a custom class.

### Plugging a different LLM

Implement `LlmClient` and provide a Spring configuration that exposes it as a bean. Existing agent code will pick it up automatically.

### Using another MCP

Implement your own `McpGateway` and `McpTools` backed by a different automation framework—such as Appium for mobile testing—and register them as Spring beans. The agent will use whichever implementation is available at runtime.

## Pre-requirements

- JDK 21 or higher
- Maven 3.9+
- OpenAI API key (or another LLM credential)
- Internet access to download dependencies

## Running

Execute the integration tests:

```bash
mvn test
```

Generate and serve the Allure report:

```bash
mvn allure:serve
```

## License

This project is released under the [PolyForm Noncommercial License 1.0.0](https://polyformproject.org/licenses/noncommercial/1.0.0). It is intended for evaluation and educational use only and may **not** be used for commercial purposes without prior permission. See the [LICENSE](LICENSE) file for the full text.

If you are interested in contributing or developing the idea further, please contact me on [LinkedIn](https://www.linkedin.com/in/nikolaevskiy).
