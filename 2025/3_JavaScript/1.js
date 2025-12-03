const fs = require("fs");

const lines = fs.readFileSync("input.txt", "utf8")
    .trim()
    .split("\n");

function find_largest(str){
    let first_digit = "0";
    let second_digit = "0";
    let first_digit_pos = 0;

    for (let i = 0; i < str.length - 1; i++){
        if(str[i] > first_digit){
            first_digit = str[i];
            second_digit = str[i+1];
            first_digit_pos = i;
        }
    }
    for (let i = first_digit_pos + 1; i < str.length; i++){
        if(str[i] > second_digit){
            second_digit = str[i];
        }
    }

    return Number(first_digit + second_digit);
}

// main
let sum = 0;
for (const line of lines){
    sum += find_largest(line);
}
console.log("Part 1: " + sum)
//17359