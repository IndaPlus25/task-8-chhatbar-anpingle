registries={
    "#0": '00',
    "#1": '01',
    "#2": '10',
    "#3": '11',
}
operators={
    "add":'000',
    "sub":'001',
    "set":'010',
    "jeq":'011',
    "j":'100',
    "input":'101',
    "print":'110',
    "exit":'111'
}
def fileline(filename):
    lines = []
    try:
        with open(filename, 'r') as f:
            lines = [line.rstrip('\n') for line in f]
    except FileNotFoundError as e:
        print("Error:", e)
        exit(1)
    return lines

def read_instructions(lines):
    fixed_lines = []
    for line in lines:
        end = line.find("//")
        if end == -1:
            line = line.strip()
        else:
            line = line[:end].strip()
        fixed_lines.append(line)
    return fixed_lines

def is_valid_op(op):
    return op in {"add", "sub", "set", "jeq", "j", "input", "print", "exit"}

def is_integer(s):
    try:
        int(s)
        return True
    except ValueError:
        return False

def is_valid_register(s):
    return s in {"#0", "#1", "#2", "#3"}

def is_valid_imm(s):
    return s in {"0", "1"}

def is_valid_instructions(instructions):
    for i, line in enumerate(instructions, start=1):
        if not line.strip():
            continue
        words = line.split()
        operator = words[0]

        if not is_valid_op(operator):
            print(f"Syntax ERROR at line {i}: Unknown operator {operator}")
            return False

        if operator in {"input", "print", "exit"}:
            if len(words) != 1:
                print(f"Syntax ERROR at line {i}: Operator {operator} takes no arguments")
                return False

        elif operator in {"add", "sub", "set", "jeq"}:
            if len(words) != 4:
                print(f"Syntax ERROR at line {i}: Operator {operator} requires 3 arguments")
                return False
            if not is_valid_register(words[1]) or not is_valid_register(words[2]):
                print(f"Syntax ERROR at line {i}: Invalid register")
                return False
            if not is_valid_imm(words[3]):
                print(f"Syntax ERROR at line {i}: Imm must be either 0 or 1")
                return False

        elif operator == "j":
            if len(words) != 2:
                print(f"Syntax ERROR at line {i}: Operator {operator} requires 1 argument")
                return False
            if not is_integer(words[1]):
                print(f"Syntax ERROR at line {i}: Operator {operator} requires 1 INTEGER argument")
                return False

    return True
def tobinary(j):
    if j < 0:
        j = 32 + j
    return format(j, '05b')

def assembler(instructions):
    binary=[]
    for line in instructions:
        if line != '':
            linesplit=line.split(' ')
            op = linesplit[0]
            if op == "input":
                binary.append("10100000")
            elif op == "print":
                binary.append("11000000")
            elif op == "exit":
                binary.append("11100000")
            elif op in ['add','sub','set','jeq']:
                binary.append(operators[op]+registries[linesplit[1]]+registries[linesplit[2]])
            elif op == "j":
                binary.append("100"+tobinary(int(linesplit[1])))
    return binary

if __name__ == "__main__":
    lines = fileline("multiply.bbvv")
    instructions = read_instructions(lines)
    if is_valid_instructions(instructions):
        # print(instructions)
        lines=assembler(instructions)
        for i in lines:
            print(i)

