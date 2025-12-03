const fs = require("fs");

const lines = fs.readFileSync("input.txt", "utf8")
    .trim()
    .split("\n");

function find_largest_digit(str, start_pos, end_post){
    let digit = "0";
    let digit_pos = 0;

    for (let i = start_pos; i <= end_post; i++){
        if(str[i] > digit){
            digit = str[i];
            digit_pos = i;
        }
    }

    return [digit, digit_pos];
}

function  find_largest_12digit_number(str){
    let num = "";
    let start_pos = 0;

    for (let i = 0; i < 12; i++){
        [digit, digit_pos] = find_largest_digit(str, start_pos, str.length-(12-i))
        num += digit;
        start_pos = digit_pos + 1;
    }

    return Number(num)
}

// main
let sum = 0;
for (const line of lines){
    sum += find_largest_12digit_number(line);
}
console.log("Part 2: " + sum)
//172787336861064