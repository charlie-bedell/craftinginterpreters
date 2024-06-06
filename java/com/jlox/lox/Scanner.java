package java.com.jlox.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.com.jlox.lox.TokenType.*;

class Scanner {
		private final String source;
		private final List<Token> tokens = new ArrayList<>();
		private int start = 0;
		private int current = 0;
		private int line = 1;

		private boolean isAtEnd() {
				return current >= source.length();
		}

		Scanner(String source) {
				this.source = source;
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

		private void scanToken() {
				char c = advance();

				switch (c) {
				case '(': addToken(LEFT_PAREN); break;
				case ')': addToken(RIGHT_PAREN); break;
				case '{': addToken(LEFT_BRACE); break;
				case '}': addToken(RIGHT_BRACE); break;
				case ',': addToken(COMMA); break;
				case '.': addToken(DOT); break;
				case '-': addToken(MINUS); break;
				case '+': addToken(PLUS); break;
				case ';': addToken(SEMICOLON); break;
				case '*': addToken(STAR); break;
				case '!':
						addToken(match('=') ? BANG_EQUAL : BANG);
				case '=':
						addToken(match('=') ? EQUAL_EQUAL : EQUAL);
				case '<':
						addToken(match('=') ? LESS_EQUAL : LESS);
				case '>':
						addToken(match('=') ? GREATER_EQUAL : EQUAL);
				case '/':
						if (match('/')) {
								// A comment goes until the end of the line.
								while (peek() != '\n' && !isAtEnd()) advance();
						} else {
								addToken(SLASH);
						}
						break;

				case ' ':
				case '\t':
				case '\r':
						break;
				case '\n':
						line++;
						break;

				case '"': string(); break;

				default:
						if (isDigit(c)) {
								number();
						} else {
								Lox.error(line, "Unexpected character.");
						}
						Lox.error(line, "Unexpected character.");
						break;
				}
		}

		private void number() {
				while (isDigit(peek())) advance();

				if (peek( '.' && isDigit(peekNext()))) {
						// Consume the "."
						advance();

						while (isDigit(peek())) advance();
				}

				addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
		}

		private void string() {
				while (peek() != '"' && !isAtEnd()) {
						if (peek() == '\n') line++;
						advance();
				}

				if (isAtEnd()) {
						Lox.error(line, "unterminated string");
						return;
				}

				advance(); // The closing ".

				// Trim surrounding quotes.
				String value = source.substring(start + 1, current - 1);
				addToken(STRING, value);
		}

		

		private boolean match(char expected) {
				if (isAtEnd()) return false;
				if (source.charAt(current) != expected) return false;

				current++;
				return true;
		}

		private char advance() {
				return source.charAt(current++);
		}

		private char peek() {
				if (isAtEnd()) return '\0';
				return source.charAt(current);
		}

		private boolean isDigit(char c) {
				return c >= '0' && c <= '9';
		}

		private void addToken(TokenType type) {
				addToken(type, null);
		}

		// java function overload, use either depending on signature
		private void addToken(TokenType type, Object literal) {
				String text = source.substring(start, current);
				tokens.add(new Token(type, text, literal, line));
		}

		
		
}
