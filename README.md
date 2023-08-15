[![Release Tests](https://img.shields.io/github/actions/workflow/status/mad201802/db-tool/release.yml?label=Release%20Tests&logo=github)](https://github.com/mad201802/db-tool/actions/workflows/release.yml)
[![Latest Release](https://badgen.net/github/release/mad201802/db-tool)](https://github.com/mad201802/db-tool/releases/latest)
[![Open Issues](https://img.shields.io/github/issues-raw/mad201802/db-tool?color=orange&logo=github)](https://github.com/mad201802/db-tool/issues)
[![Commits](https://badgen.net/github/commits/mad201802/db-tool/main)](https://github.com/mad201802/db-tool/commits)
[![Pull Requests](https://badgen.net/github/prs/mad201802/db-tool)](https://github.com/mad201802/db-tool/pulls) \
![amazing](https://img.shields.io/badge/amazing-yes-blueviolet)
![fast](https://img.shields.io/badge/lightning-fast-blueviolet)

# DB-Tool - a Database Application Project

Db-tool is a database java-based CLI application that allows you to manage your database. It can list all available tables in a database and is able to search through it like grep!
The use of so called `Profiles` will make your life easier when managing a lot of different databases. Configure once and use it through out the tool.

## Features <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Star-Struck.png" alt="Star-Struck" width="25" height="25" style="transform: translateY(5px)" />

- Create a profile for different databases with `create-profile`
- List all available profiles `list-profiles`
- List all available tables in a database `list-tables`
- Use grep to search through a table `grep`
  - Search through a database with a specific column pattern (with * as wildcards) `grep -cp`
  - Search through a database with a specific column regex pattern `grep -cr`
  - Search through a table with a specific column pattern (with * as wildcards) `grep -cp`
  - Search through a table with a specific column regex pattern `grep -cr`
  - Search through table content with a specific value pattern (with * as wildcards) `grep -vp`
  - Search through table content with a specific value compare `grep -vc`
  - Limit the number of rows in the result with `grep -lr`
  - Limit the maximal length of text when the table is printed with `grep -lt`
  - Most important **You can use as many as you like of the above commands**
  - Use AND operator when using multiple `-vc` with `grep --vc-use-and`
  - Verbose logging with `--please-tell-me-everything`

## Installation <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Floppy%20Disk.png" alt="Floppy Disk" width="25" height="25" style="transform: translateY(5px)" />

Please refer to our [How To Install](https://github.com/mad201802/db-tool/wiki/03-%E2%80%90-How-To-Install) Wiki Page

## Usage <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Electric%20Plug.png" alt="Electric Plug" width="25" height="25" style="transform: translateY(5px)" />

Please refer to our [How To Use](https://github.com/mad201802/db-tool/wiki/03-%E2%80%90-How-To-Install)

## Troubleshooting <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Screwdriver.png" alt="Screwdriver" width="25" height="25" style="transform: translateY(5px)" />

In case you encounter any problems, feel free to contact us via [email](md148@hdm-stuttgart.de) or open an [issue](https://github.com/mad201802/db-tool/issues/new/choose) on GitHub.

## Known bugs

- Depending on which shell you use, the used color codes may not work (currently not working on Windows Command Prompt). This leads to weird characters in the output.

## Contributing <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Handshake.png" alt="Handshake" width="25" height="25" style="transform: translateY(5px)" />

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests accordingly.