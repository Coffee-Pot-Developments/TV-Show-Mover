# 📦 TV Mover & Organiser

A modular Java utility for scanning, parsing, renaming, organising, and moving TV episode files into a clean library structure.  
Designed for clarity, safety, and extensibility — ideal for anyone who wants Sonarr‑style organisation without the overhead.

---

## Features

### Smart File Scanning
- Recursively scans a source directory
- Identifies video files by extension
- Skips destination directory to avoid loops
- Separates matching vs non‑matching files

### Episode Parsing
Understands multiple naming formats, including:

- `Show.Name.S01E02.mkv`
- `Show Name - 1x02 - Title.mp4`
- `Show.Name.102.mkv`
- `S01E01E02` (multi‑episode)

Extracts:
- Show name
- Season number
- Episode number(s)
- File extension

### Filename Normalisation
Cleans messy release names and formats them consistently:

```
TV Show  - S01E01.mkv
```

Supports multi‑episode formatting:

```
TV Show - S01E01-E02.mkv
```

### Auto Folder Organisation
Automatically creates:

```
Show Name/
    Season 01/
```

Places renamed files in the correct location.

### Safe Move Workflow
- Full dry‑run preview
- Real move operation
- Automatic folder creation
- Logging for all actions

### 🗑Controlled Deletion
- Dry‑run delete
- Real delete
- Only affects non‑matching files

### ⚙JSON Configuration
All settings live in `config.json`:

```json
{
  "source": "D:/TV Series",
  "destination": "D:/TV Series/complete",
  "extensions": [".mkv", ".mp4", ".avi"]
}
```

---

## Project Structure

```
io/github/vertanzil/tvmover/
    Main.java

    operations/
        FileScanner.java
        FileMover.java
        FileDeleter.java
        EpisodeParser.java
        EpisodeInfo.java
        FileRenamer.java
        FolderOrganizer.java

    other/
        CommandMenu.java
        LoggerConfig.java
        ScanResult.java

    config/
        Config.java
        ConfigLoader.java

config.json
```

### Package Responsibilities

| Package | Purpose |
|--------|---------|
| `operations` | Core logic: scanning, parsing, renaming, organising, moving, deleting |
| `other` | UI, logging, and data containers |
| `config` | Config loading and representation |
| root | Entry point (`Main.java`) |

---

## 🖥Command Menu

When you run the program, you get a clean command palette:

```
[h]   Help / Show menu
[s]   Stats
[m]   Show matching files
[n]   Show non-matching files
[dm]  Dry-run organise + move
[mv]  Organise + move files
[dd]  Dry-run delete
[del] Delete files
[r]   Reload scan
[q]   Quit
```

### Typical Workflow

1. Run `dm` to preview how files will be renamed and organised
2. If everything looks correct, run `mv`
3. Optionally clean up junk with `dd` → `del`

---

## How It Works

### 1. Scan
`FileScanner` finds all video files and separates them from non‑matching files.

### 2. Parse
`EpisodeParser` extracts show/season/episode info.

### 3. Rename
`FileRenamer` generates a clean, standardised filename.

### 4. Organise
`FolderOrganizer` creates the correct show + season folders.

### 5. Move
`FileMover` performs the actual move (or dry‑run preview).

### 6. Delete
`FileDeleter` removes unwanted files safely.

---

## 🛠Requirements

- Java 17+
- Gson (for JSON config loading)

---

## Configuration
Rename ExampleConfig.json to config.json.
Edit `config.json` to set:

- Source directory
- Destination directory
- Video extensions

Example:

```json
{
  "source": "D:/TV Series",
  "destination": "D:/TV Series/complete",
  "extensions": [".mkv", ".mp4", ".avi"]
}
```

---

## License

GPLv3.0

---

