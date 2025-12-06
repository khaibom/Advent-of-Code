import scala.annotation.tailrec
import scala.io.Source

def readLines(path: String): List[List[String]] = Source.fromFile(path).getLines().map(_.split("\\s+").toList).toList


def rotate(lines: List[List[String]]): List[List[String]] = lines.transpose


val lines = rotate(readLines("input.txt"))

def sum(a: BigInt, b: BigInt): BigInt = a+b
def prod(a: BigInt, b: BigInt): BigInt = a*b

@tailrec
def getTheLastElement(xs: List[String], last: String) : String = xs match{
  case Nil => last
  case y::ys => getTheLastElement(ys, y)
}

@tailrec
def removeTheLastElement(xs: List[String], result:List[BigInt]) : List[BigInt] = xs match{
  case x::Nil => result
  case y::ys => removeTheLastElement(ys, result:+BigInt(y.toInt))
}

def separate(xs: List[String]) : (List[BigInt], String) = (removeTheLastElement(xs, Nil), getTheLastElement(xs, ""))

def reduce(f: (BigInt, BigInt) => BigInt, init: BigInt)(xs: List[BigInt]) : BigInt = {
  @tailrec
  def reduceHelp(xs: List[BigInt], result: BigInt): BigInt = xs match {
    case Nil => result
    case y::ys => reduceHelp(ys, f(y, result))
  }
  reduceHelp(xs, init)
}

@tailrec
def p1(xs: List[List[String]], result: BigInt) : BigInt = xs match{
  case Nil => result
  case y::ys => {
    val (line, last) = separate(y)
    last match{
      case "+" => p1(ys, reduce(sum, 0)(line)+result)
      case "*" => p1(ys, reduce(prod, 1)(line)+result)
    }
  }
}
p1(lines, 0) // 6605396225322



val p2_lines: List[String] = Source.fromFile("input.txt").getLines().toList

case class newChar(char: Char, x: Int, y: Int)

@tailrec
def findPostInLine(line: List[Char], x_pos: Int, y_pos: Int, newCharList: List[newChar]) : List[newChar] = line match{
  case Nil => newCharList
  case x::xs => findPostInLine(xs, x_pos+1, y_pos, newCharList:+newChar(x, x_pos, y_pos))
}

@tailrec
def findPos(lines: List[List[Char]], x_pos: Int, y_pos: Int, newCharList: List[newChar]) : List[newChar] = lines match{
  case Nil => newCharList
  case line::lines =>
    val lineChar = findPostInLine(line, 0, y_pos, Nil)
    findPos(lines, 0, y_pos+1, newCharList:::lineChar)
}

def convertStringToCharList(lines: List[String], result:List[List[Char]]):List[List[Char]] = lines match{
  case Nil => result
  case line::lines => convertStringToCharList(lines, result:::List(line.toList))
}

val p2_line_pos = findPos(convertStringToCharList(p2_lines, Nil), 0, 0, Nil)
def mergeColsFromNewChar(chars: List[newChar]): List[String] = {
  val groupedByX: Map[Int, List[newChar]] = chars.groupBy(_.x)
  groupedByX.toList
    .sortBy { case (x, _) => -x }
    .map { case (_, list) => list.sortBy(_.y).map(_.char).mkString}
}

val p2_new_lines = mergeColsFromNewChar(p2_line_pos)

def tokenize(s: String): List[String] = s.trim match {
  case "" => Nil
  case t if t.matches("""\d+\s*[+*]""") =>
    val num = t.takeWhile(_.isDigit)
    val op  = t.dropWhile(_.isDigit).trim
    List(num, op)
  case t if t.matches("""\d+""") => List(t)
  case t @ ("+" | "*") => List(t)
  case _ => Nil
}

@tailrec
def groupTokens(inputs: List[String], current: List[String], result: List[List[String]]): List[List[String]] = inputs match {
  case Nil => current match {
    case Nil    => result
    case x => result :+ x
  }
  case str::rest => tokenize(str) match {
    case Nil => groupTokens(rest, current, result)
    case tokens =>
      val (numbers, ops) = tokens.partition(_.forall(_.isDigit))
      ops match {
        case Nil => groupTokens(rest, current ::: numbers, result)
        case op :: Nil =>
          val finished = (current ::: numbers) :+ op
          groupTokens(rest, Nil, result :+ finished)
      }
  }
}
val p2_input = groupTokens(p2_new_lines, Nil, Nil)


def p2(xs: List[List[String]], result: BigInt) : BigInt = xs match{
  case Nil => result
  case y::ys => {
    val (line, last) = separate(y)
    last match{
      case "+" => p1(ys, reduce(sum, 0)(line)+result)
      case "*" => p1(ys, reduce(prod, 1)(line)+result)
    }
  }
}
p2(p2_input, 0) //11052310600986