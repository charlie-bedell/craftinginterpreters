package java.com.jlox.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.com.jlox.lox.TokenType.*;

class Scanner {
		private final String source;
		private final List<Token> tokens = new ArrayList<>();

		Scanner(String source) {
				this.source = source;
		}
}

List<Token> scanTokens() {
		while (!isAtEnd()) {
				// we are at the beginning of the next lexeme
				start = current;
				scanToken();
		}

		tokens.add(new Token(EOF, "", null, line));
		return tokens;
}
