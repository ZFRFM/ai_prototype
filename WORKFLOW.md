---
tracker:
  kind: linear
  api_key: $LINEAR_API_KEY
  required_labels: [ai-ready]
  active_states: [Todo, In Progress]
  terminal_states: [Done, Canceled, Cancelled, Duplicate, In Review]

polling:
  interval_ms: 30000

workspace:
  root: ~/symphony_workspaces/prototype

agent:
  max_concurrent_agents: 1
  max_turns: 8
  max_retry_backoff_ms: 300000

codex:
  command: codex app-server
  turn_timeout_ms: 3600000
  read_timeout_ms: 5000
  stall_timeout_ms: 300000

git:
  branch_prefix: symphony
  require_clean_workspace: true
---

You are working on issue {{ issue.identifier }}: {{ issue.title }}.

Goal:
- Implement the issue fully in this Android project.
- Create a branch named `symphony/{{ issue.identifier }}`.
- Make the smallest correct change that satisfies the acceptance criteria.
- Follow the repository instructions in `AGENTS.md`.

Expected workflow:
- Read the issue title, description, and acceptance criteria.
- Inspect the relevant app code before editing.
- Update or add tests when behavior changes.
- Run:
  - `./gradlew :app:testDebugUnitTest`
  - `./gradlew :app:lintDebug`
  - `./gradlew :app:assembleDebug`
- Fix failures caused by your change.
- Commit the work with a concise message that includes the issue identifier.
- Push the branch and open a pull request.
- Include changed files and verification results in the pull request summary.
- Move the issue to `In Review`, not `Done`.

Safety rules:
- Do not auto-merge.
- Do not change secrets, signing files, or local SDK configuration.
- Stop and report if the task requires credentials, paid services, or Android
  Studio-only manual actions.
