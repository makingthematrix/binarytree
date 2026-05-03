# AGENTS.md

## Project Overview
This is a Scala project for implementing and testing binary tree algorithms.

## Repository Structure
- `src/main/scala/binarytree/`: Core implementation
- `src/test/scala/binarytree/`: Unit tests

## Development Workflow
1. Clone the repository: `git clone https://github.com/makingthematrix/binarytree.git`.
2. Run tests: `sbt test`.
3. Run the project: `sbt run`.

## Coding Standards
- Use Scala 3 with braces syntax.

## GitHub Workflow
- Create a new branch for each feature or bug fix.
- Use `feature/xxx` for new features.
- Use `bugfix/xxx` for bug fixes.
- Commit changes with `git commit -am "[MESSAGE]"`
- Push changes to the branch with `git push`
- Don't merge the branch to the main branch directly.

## AI-Specific Guidelines
- Always run `sbt test` before committing.
- Do not modify files in `target/`.
- Prefer functional programming patterns.

## Important Notes
- The `Tree.scala` file is the main file for binary tree algorithms.
- Avoid modifying the `build.sbt` file unless necessary.

## Contacts
- Maintainer: [@makingthematrix](https://github.com/makingthematrix)