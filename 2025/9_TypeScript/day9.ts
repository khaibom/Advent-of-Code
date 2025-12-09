import * as fs from 'fs';

type Point = {
    x: number;
    y: number;
};
function readInput(path: string): Point[]{
    const fileData = fs.readFileSync(path, 'utf-8');
    return fileData
        .trim()
        .split("\n")
        .map(line => {
            const [a, b] = line.split(",");
            return {x: Number(a),  y: Number(b)};
        });
}

function p1(points: Point[]){
    let max: number = 0;
    for (const point1 of points){
        for (const point2 of points){
            let width: number = Math.abs(point1.x-point2.x) + 1;
            let length: number = Math.abs(point1.y-point2.y) + 1;
            let area: number = width * length;
            if(area>=max) max = area;
        }
    }
    console.log(max);
}
const points = readInput('input.txt');
p1(points)
//4739623064