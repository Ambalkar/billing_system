# Competitive Programming Lab - Exam Solutions

## Practical 1 - Introduction to Online Judge

### Problem 1: The 3n + 1 Problem

**1. Name of Problem Statement:** The 3n + 1 Problem (Collatz Conjecture)

**2. Theory/Explanation of Problem:**
The 3n + 1 problem involves a simple algorithm applied to positive integers:
- If n is even, divide it by 2
- If n is odd, multiply by 3 and add 1
- Repeat until n becomes 1
The problem asks to find the maximum cycle length for all numbers in a given range [i, j].

**3. Algorithm/Solution Logic:**
1. For each number in range [i, j], calculate its cycle length
2. Keep track of the maximum cycle length found
3. Return the maximum cycle length for the range

**4. Sample Input and Output:**
```
Input: 1 10
Output: 1 10 20

Input: 100 200
Output: 100 200 125
```

**5. Python Code:**
```python
def cycle_length(n):
    length = 1
    while n != 1:
        if n % 2 == 0:
            n = n // 2
        else:
            n = 3 * n + 1
        length += 1
    return length

def max_cycle_length(i, j):
    if i > j:
        i, j = j, i
    max_length = 0
    for num in range(i, j + 1):
        length = cycle_length(num)
        max_length = max(max_length, length)
    return max_length

# Main execution
while True:
    try:
        line = input().split()
        i, j = int(line[0]), int(line[1])
        result = max_cycle_length(i, j)
        print(f"{i} {j} {result}")
    except EOFError:
        break
```

---

### Problem 2: Minesweeper

**1. Name of Problem Statement:** Minesweeper

**2. Theory/Explanation of Problem:**
Given a rectangular minefield with mines marked as '*', calculate the number of mines adjacent to each empty cell. Each cell has at most 8 neighbors (horizontally, vertically, and diagonally adjacent).

**3. Algorithm/Solution Logic:**
1. Read the minefield dimensions and grid
2. For each non-mine cell, count adjacent mines in all 8 directions
3. Output the grid with mine counts

**4. Sample Input and Output:**
```
Input:
4 4
*...
....
.*..
....
0 0

Output:
Field #1:
*100
2210
1*10
1110
```

**5. Python Code:**
```python
def count_adjacent_mines(field, row, col, rows, cols):
    count = 0
    directions = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1)]
    
    for dr, dc in directions:
        new_row, new_col = row + dr, col + dc
        if 0 <= new_row < rows and 0 <= new_col < cols:
            if field[new_row][new_col] == '*':
                count += 1
    return count

field_num = 1
while True:
    rows, cols = map(int, input().split())
    if rows == 0 and cols == 0:
        break
    
    if field_num > 1:
        print()
    
    field = []
    for _ in range(rows):
        field.append(input().strip())
    
    print(f"Field #{field_num}:")
    for i in range(rows):
        row_output = ""
        for j in range(cols):
            if field[i][j] == '*':
                row_output += '*'
            else:
                count = count_adjacent_mines(field, i, j, rows, cols)
                row_output += str(count)
        print(row_output)
    
    field_num += 1
```

---

## Practical 2 - Introduction to Online Judge

### Problem 3: LCD Display

**1. Name of Problem Statement:** LCD Display

**2. Theory/Explanation of Problem:**
Display digits in LCD format using horizontal and vertical segments. Each digit is displayed using a pattern of dashes (-) and pipes (|) with a specified size.

**3. Algorithm/Solution Logic:**
1. Define LCD patterns for each digit (0-9)
2. For each digit, generate the LCD representation with given size
3. Print the LCD display line by line

**4. Sample Input and Output:**
```
Input: 2 12345
Output:
      --  --      -- 
   |    |   | |  |   
   |    |   | |  |   
      --  --  --  -- 
   |       |   |   | 
   |       |   |   | 
      --  --      -- 
```

**5. Python Code:**
```python
def get_lcd_patterns():
    patterns = {
        '0': [" - ", "| |", "   ", "| |", " - "],
        '1': ["   ", "  |", "   ", "  |", "   "],
        '2': [" - ", "  |", " - ", "|  ", " - "],
        '3': [" - ", "  |", " - ", "  |", " - "],
        '4': ["   ", "| |", " - ", "  |", "   "],
        '5': [" - ", "|  ", " - ", "  |", " - "],
        '6': [" - ", "|  ", " - ", "| |", " - "],
        '7': [" - ", "  |", "   ", "  |", "   "],
        '8': [" - ", "| |", " - ", "| |", " - "],
        '9': [" - ", "| |", " - ", "  |", " - "]
    }
    return patterns

def print_lcd(size, number):
    patterns = get_lcd_patterns()
    
    # Generate LCD representation
    lcd_lines = [""] * (2 * size + 3)
    
    for digit in number:
        pattern = patterns[digit]
        
        # Top horizontal segment
        lcd_lines[0] += " " + pattern[0][1] * size + " "
        
        # Upper vertical segments
        for i in range(1, size + 1):
            lcd_lines[i] += pattern[1][0] + " " * size + pattern[1][2]
        
        # Middle horizontal segment
        lcd_lines[size + 1] += " " + pattern[2][1] * size + " "
        
        # Lower vertical segments
        for i in range(size + 2, 2 * size + 2):
            lcd_lines[i] += pattern[3][0] + " " * size + pattern[3][2]
        
        # Bottom horizontal segment
        lcd_lines[2 * size + 2] += " " + pattern[4][1] * size + " "
        
        # Add space between digits (except for last digit)
        if digit != number[-1]:
            for i in range(len(lcd_lines)):
                lcd_lines[i] += " "
    
    # Print the LCD display
    for line in lcd_lines:
        print(line.rstrip())

# Main execution
while True:
    try:
        line = input().split()
        if len(line) != 2:
            break
        size, number = int(line[0]), line[1]
        if size == 0:
            break
        print_lcd(size, number)
        print()
    except EOFError:
        break
```

---

### Problem 4: Interpreter

**1. Name of Problem Statement:** Interpreter

**2. Theory/Explanation of Problem:**
Simulate a simple computer with 10 registers (00-09) and specific instruction set. Execute instructions until HALT (100) is encountered or infinite loop is detected.

**3. Algorithm/Solution Logic:**
1. Parse and store all instructions
2. Execute instructions one by one using program counter
3. Detect infinite loops by tracking execution counts
4. Handle register operations and conditional jumps

**4. Sample Input and Output:**
```
Input:
1
8
100
200 00 01
300 01 02
400 02 00
500 01 00
600 01 03
700 02 01
800 01 00
100
00 00

Output:
5
```

**5. Python Code:**
```python
def execute_program(instructions, registers):
    pc = 0  # program counter
    execution_count = [0] * len(instructions)
    max_executions = 50000  # to detect infinite loops
    
    while pc < len(instructions):
        if execution_count[pc] > max_executions:
            return -1  # infinite loop
        
        execution_count[pc] += 1
        instruction = instructions[pc]
        
        opcode = instruction // 100
        reg1 = (instruction % 100) // 10
        reg2 = instruction % 10
        
        if opcode == 1:  # HALT
            return sum(execution_count)
        elif opcode == 2:  # SET
            registers[reg1] = reg2
        elif opcode == 3:  # ADD
            registers[reg1] = (registers[reg1] + registers[reg2]) % 1000
        elif opcode == 4:  # MULTIPLY
            registers[reg1] = (registers[reg1] * registers[reg2]) % 1000
        elif opcode == 5:  # SET from register
            registers[reg1] = registers[reg2]
        elif opcode == 6:  # ADD from register
            registers[reg1] = (registers[reg1] + registers[reg2]) % 1000
        elif opcode == 7:  # MULTIPLY from register
            registers[reg1] = (registers[reg1] * registers[reg2]) % 1000
        elif opcode == 8:  # SET from memory
            registers[reg1] = registers[registers[reg2]]
        elif opcode == 9:  # SET memory
            registers[registers[reg2]] = registers[reg1]
        elif opcode == 0:  # JUMP if not zero
            if registers[reg2] != 0:
                pc = reg1
                continue
        
        pc += 1
    
    return sum(execution_count)

# Main execution
cases = int(input())
for _ in range(cases):
    if _ > 0:
        print()
    
    n = int(input())
    instructions = []
    for _ in range(n):
        instructions.append(int(input()))
    
    registers = [0] * 10
    reg_input = input().split()
    for i in range(len(reg_input)):
        registers[i] = int(reg_input[i])
    
    result = execute_program(instructions, registers)
    print(result)
```

---

## Practical 3 - Elementary Data Structures

### Problem 5: Jolly Jumpers

**1. Name of Problem Statement:** Jolly Jumpers

**2. Theory/Explanation of Problem:**
A sequence of n integers is called jolly if the absolute differences between consecutive elements are all different and form the set {1, 2, 3, ..., n-1}.

**3. Algorithm/Solution Logic:**
1. Calculate absolute differences between consecutive elements
2. Check if all differences are unique and form set {1, 2, ..., n-1}
3. Return "Jolly" or "Not jolly"

**4. Sample Input and Output:**
```
Input: 4 1 4 2 3
Output: Jolly

Input: 5 1 4 2 -1 6
Output: Not jolly
```

**5. Python Code:**
```python
def is_jolly(sequence):
    n = len(sequence)
    if n == 1:
        return True
    
    differences = set()
    for i in range(1, n):
        diff = abs(sequence[i] - sequence[i-1])
        differences.add(diff)
    
    expected_differences = set(range(1, n))
    return differences == expected_differences

# Main execution
while True:
    try:
        line = input().split()
        n = int(line[0])
        sequence = [int(x) for x in line[1:]]
        
        if is_jolly(sequence):
            print("Jolly")
        else:
            print("Not jolly")
    except EOFError:
        break
```

---

### Problem 6: Poker Hands

**1. Name of Problem Statement:** Poker Hands

**2. Theory/Explanation of Problem:**
Compare two poker hands and determine which one wins according to standard poker rules. Handle all poker hand rankings from high card to straight flush.

**3. Algorithm/Solution Logic:**
1. Parse each hand and determine its ranking
2. Compare hands based on poker hierarchy
3. Handle tie-breaking rules for each hand type

**4. Sample Input and Output:**
```
Input: 2H 3D 5S 9C KD 2C 3H 4S 8C AH
Output: White wins.

Input: 2H 4S 4C 2D 4H 2S 8S AS QS 3S
Output: Black wins.
```

**5. Python Code:**
```python
def parse_card(card):
    values = {'2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, 
              '9': 9, 'T': 10, 'J': 11, 'Q': 12, 'K': 13, 'A': 14}
    return (values[card[0]], card[1])

def get_hand_rank(hand):
    cards = [parse_card(card) for card in hand]
    values = sorted([card[0] for card in cards])
    suits = [card[1] for card in cards]
    
    is_flush = len(set(suits)) == 1
    is_straight = all(values[i] == values[i-1] + 1 for i in range(1, 5))
    
    # Special case for A-2-3-4-5 straight
    if values == [2, 3, 4, 5, 14]:
        is_straight = True
        values = [1, 2, 3, 4, 5]
    
    value_counts = {}
    for v in values:
        value_counts[v] = value_counts.get(v, 0) + 1
    
    counts = sorted(value_counts.values(), reverse=True)
    
    if is_straight and is_flush:
        return (8, max(values))  # Straight flush
    elif counts == [4, 1]:
        return (7, [v for v, c in value_counts.items() if c == 4][0])  # Four of a kind
    elif counts == [3, 2]:
        return (6, [v for v, c in value_counts.items() if c == 3][0])  # Full house
    elif is_flush:
        return (5, sorted(values, reverse=True))  # Flush
    elif is_straight:
        return (4, max(values))  # Straight
    elif counts == [3, 1, 1]:
        return (3, [v for v, c in value_counts.items() if c == 3][0])  # Three of a kind
    elif counts == [2, 2, 1]:
        pairs = sorted([v for v, c in value_counts.items() if c == 2], reverse=True)
        return (2, pairs)  # Two pairs
    elif counts == [2, 1, 1, 1]:
        return (1, [v for v, c in value_counts.items() if c == 2][0])  # One pair
    else:
        return (0, sorted(values, reverse=True))  # High card

def compare_hands(black_hand, white_hand):
    black_rank = get_hand_rank(black_hand)
    white_rank = get_hand_rank(white_hand)
    
    if black_rank[0] > white_rank[0]:
        return "Black wins."
    elif white_rank[0] > black_rank[0]:
        return "White wins."
    else:
        # Same rank, compare values
        if black_rank[1] > white_rank[1]:
            return "Black wins."
        elif white_rank[1] > black_rank[1]:
            return "White wins."
        else:
            return "Tie."

# Main execution
while True:
    try:
        line = input().split()
        black_hand = line[:5]
        white_hand = line[5:]
        print(compare_hands(black_hand, white_hand))
    except EOFError:
        break
```

---

## Practical 4 - Elementary Data Structures

### Problem 7: Hartal

**1. Name of Problem Statement:** Hartal

**2. Theory/Explanation of Problem:**
Calculate the number of working days lost due to hartals (strikes) called by different political parties. Each party calls hartal at regular intervals, and if multiple parties call hartal on the same day, it's counted as one lost day. Fridays and Saturdays are weekends (non-working days).

**3. Algorithm/Solution Logic:**
1. Mark all hartal days for each party based on their intervals
2. Count unique hartal days that fall on working days (Sunday-Thursday)
3. Exclude weekends from the count

**4. Sample Input and Output:**
```
Input:
2
100
4
3 4 8 10

14
2
12 15

Output:
5
1
```

**5. Python Code:**
```python
def count_hartal_days(n, parties):
    hartal_days = set()
    
    for interval in parties:
        day = interval
        while day <= n:
            # Check if it's not a weekend (Friday=6, Saturday=7 in 1-indexed week)
            day_of_week = ((day - 1) % 7) + 1
            if day_of_week != 6 and day_of_week != 7:  # Not Friday or Saturday
                hartal_days.add(day)
            day += interval
    
    return len(hartal_days)

# Main execution
test_cases = int(input())
for _ in range(test_cases):
    n = int(input())
    num_parties = int(input())
    parties = list(map(int, input().split()))
    
    result = count_hartal_days(n, parties)
    print(result)
```

---

### Problem 8: Stack them up

**1. Name of Problem Statement:** Stack them up

**2. Theory/Explanation of Problem:**
Simulate card shuffling operations on a deck of 52 cards. Given a series of shuffle operations, determine the final order of cards after all shuffles are applied.

**3. Algorithm/Solution Logic:**
1. Initialize deck with cards 1-52
2. For each shuffle operation, rearrange cards according to given permutation
3. Output final card positions

**4. Sample Input and Output:**
```
Input:
1

1
2 1 3 4 5 ... 52

1

Output:
2
1
3
4
...
52
```

**5. Python Code:**
```python
def apply_shuffle(deck, shuffle_pattern):
    new_deck = [0] * 52
    for i in range(52):
        new_deck[i] = deck[shuffle_pattern[i] - 1]
    return new_deck

# Main execution
test_cases = int(input())
first_case = True

for _ in range(test_cases):
    if not first_case:
        print()
    first_case = False
    
    input()  # blank line
    
    # Read shuffle patterns
    shuffles = []
    try:
        while True:
            line = input().strip()
            if not line:
                break
            shuffle = list(map(int, line.split()))
            if len(shuffle) == 52:
                shuffles.append(shuffle)
    except EOFError:
        pass
    
    # Read shuffle sequence
    shuffle_sequence = []
    try:
        while True:
            line = input().strip()
            if line:
                shuffle_sequence.append(int(line) - 1)  # Convert to 0-indexed
    except EOFError:
        pass
    
    # Initialize deck
    deck = list(range(1, 53))
    
    # Apply shuffles
    for shuffle_index in shuffle_sequence:
        if shuffle_index < len(shuffles):
            deck = apply_shuffle(deck, shuffles[shuffle_index])
    
    # Output final deck
    for card in deck:
        print(card)
```

---

## Practical 5 - Strings

### Problem 9: WERTYU

**1. Name of Problem Statement:** WERTYU

**2. Theory/Explanation of Problem:**
When typing, hands are shifted one position to the right on the QWERTY keyboard. Given the typed text, determine what was actually intended to be typed.

**3. Algorithm/Solution Logic:**
1. Create mapping from shifted positions to correct positions
2. For each character, find its correct position (one key to the left)
3. Handle special cases for punctuation and spaces

**4. Sample Input and Output:**
```
Input: O S, PPRPD.
Output: I AM GOOD.

Input: YPFSU VSAEU RF YPFSU EPTRD
Output: WORLD CLASS OF WORLD POINT
```

**5. Python Code:**
```python
def create_keyboard_mapping():
    qwerty_rows = [
        "`1234567890-=",
        "QWERTYUIOP[]\\",
        "ASDFGHJKL;'",
        "ZXCVBNM,./"
    ]
    
    mapping = {}
    for row in qwerty_rows:
        for i in range(1, len(row)):
            mapping[row[i]] = row[i-1]
    
    return mapping

def decode_wertyu(text):
    mapping = create_keyboard_mapping()
    result = ""
    
    for char in text:
        if char in mapping:
            result += mapping[char]
        else:
            result += char  # Space or other characters remain unchanged
    
    return result

# Main execution
while True:
    try:
        line = input()
        print(decode_wertyu(line))
    except EOFError:
        break
```

---

### Problem 10: Crypt Kicker II

**1. Name of Problem Statement:** Crypt Kicker II

**2. Theory/Explanation of Problem:**
Decode encrypted text where each letter is consistently replaced by another letter. Use frequency analysis and pattern matching to determine the substitution cipher.

**3. Algorithm/Solution Logic:**
1. Analyze letter frequencies in encrypted text
2. Use common English letter frequencies for initial mapping
3. Refine mapping using word patterns and dictionary matching
4. Apply substitution to decode the message

**4. Sample Input and Output:**
```
Input:
bjvg xsb hxsn xsb rqat xsb bjvg
xxxx yyy zzzz yyy xxxx yyy xxxx

Output:
dick and jane and spot and dick
**** *** **** *** **** *** ****
```

**5. Python Code:**
```python
def analyze_patterns(encrypted_line, known_line):
    mapping = {}
    reverse_mapping = {}
    
    enc_words = encrypted_line.split()
    known_words = known_line.split()
    
    if len(enc_words) != len(known_words):
        return None
    
    for enc_word, known_word in zip(enc_words, known_words):
        if len(enc_word) != len(known_word):
            return None
        
        for enc_char, known_char in zip(enc_word, known_word):
            if enc_char in mapping:
                if mapping[enc_char] != known_char:
                    return None
            else:
                if known_char in reverse_mapping:
                    if reverse_mapping[known_char] != enc_char:
                        return None
                mapping[enc_char] = known_char
                reverse_mapping[known_char] = enc_char
    
    return mapping

def apply_mapping(text, mapping):
    result = ""
    for char in text:
        if char.isalpha():
            if char.lower() in mapping:
                decoded = mapping[char.lower()]
                result += decoded.upper() if char.isupper() else decoded
            else:
                result += '*'
        else:
            result += char
    return result

# Main execution
while True:
    try:
        encrypted_line = input().strip()
        if not encrypted_line:
            break
        
        known_line = input().strip()
        
        mapping = analyze_patterns(encrypted_line.lower(), known_line.lower())
        
        if mapping:
            # Try to decode with current mapping
            result = apply_mapping(encrypted_line, mapping)
            print(result)
        else:
            # If no val