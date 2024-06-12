# Variables
JAVAC = javac
JAVA = java
SRC_DIR = src
PKG_DIR = src/com/jlox/lox
BIN_DIR = bin
MAIN_CLASS = com.jlox.lox.Lox

# Find all .java files in the PKG_DIR
SOURCES := $(shell find $(PKG_DIR) -name '*.java')

# Create a list of corresponding .class files in the BIN_DIR
CLASSES := $(SOURCES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Default target
.PHONY: all
all: $(CLASSES)

# Rule to compile .java files to .class files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(dir $@)
	$(JAVAC) -d $(BIN_DIR) -sourcepath $(SRC_DIR) $<

# Clean target to remove compiled classes
.PHONY: clean
clean:
	rm -rf $(BIN_DIR)

# Run the main class
.PHONY: run
run: $(CLASSES)
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)
