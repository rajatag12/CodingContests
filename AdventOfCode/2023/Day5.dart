import 'package:collection/collection.dart';
import 'dart:math';

void main() {
  String test = '''
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4''';

  final lines = test.split('\n');
  final numExp = RegExp(r'\d+');
  List<int> seeds =
      numExp.allMatches(lines[0]).map((m) => int.parse(m[0]!)).toList();
  List<Mapping> mappings = [];

  // input
  lines.sublist(1).forEach((line) {
    if (line.isEmpty) return;
    if (line.trim().startsWith(RegExp(r'[a-z]'))) {

      mappings.add(Mapping());
      return;
    }
    final intValues =
        numExp.allMatches(line).map((m) => int.parse(m[0]!)).toList();
    mappings.last.ranges.add(Range(intValues));
    mappings.last.sortRanges();
  });

  // pre-compute
  final precomputedMapping = compressMappingsFromSeed(mappings);
  // compute
  int res = part2(seeds, precomputedMapping);

  // result
  print('res=$res');
}

int part1(List<int> seeds, List<Mapping> mappings) {
  int res = 1 << 30;
  for (int s in seeds) {
    int v = s;
    for (Mapping m in mappings) {
      v = m.getDest(v);
    }
    if (v < res) res = v;
  }
  return res;
}

int part2(List<int> seeds, Mapping mapping) {
  // mapping.printRanges();
  int res = 1 << 30;
  for (int i = 0; i < seeds.length; i += 2) {
    int first = seeds[i];
    int last = seeds[i] + seeds[i + 1] - 1;
    int ri = 0;
    while (first <= last && ri < mapping.ranges.length) {
      // print('first: $first, last: $last, ri: $ri');
      final r = mapping.ranges[ri];
      if (r.srcStart > first) {
        // first before r.start
        res = min(res, first);
        first = min(r.srcStart, last);
      } else if (first > r.srcLast) {
        // first after r.last
        ri++;
      } else {
        // first intersects with r
        res = min(res, first + r.offset);
        first = min(r.srcLast + 1, last + 1);
      }
    }
    if (ri == mapping.ranges.length) {
      print('breakkkkkkkkkk');
      res = min(res, first);
    }
  }
  return res;
}

Mapping compressMappingsFromSeed(List<Mapping> mappings) {
  Mapping res = mappings[0];
  for (final m in mappings) {
    if (res == m) {
      continue;
    }

    final next = Mapping();
    for (final r in res.ranges) {
      int first = r.srcStart + r.offset;
      int last = r.srcLast + r.offset;
      m.firstRange();
      while (first <= last) {
        if (first < m.curRange.srcStart) {
          final nextlast = min(m.curRange.srcStart - 1, last);
          next.ranges
              .add(Range([first, first - r.offset, nextlast - first + 1]));
          first = nextlast + 1;
        } else if (first > m.curRange.srcLast) {
          if (m.hasMoreRanges) {
            m.nextRange();
          } else {
            break;
          }
        } else {
          final nextlast = min(last, m.curRange.srcLast);
          next.ranges.add(Range([
            first + m.curRange.offset,
            first - r.offset,
            nextlast - first + 1
          ]));
          first = nextlast + 1;
        }
      }

      if (first <= last) {
        next.ranges
            .add(Range([first, first - r.offset, last - first + 1]));
      }
    }

    next.sortRanges();
    res = next;
  }

  return res;
}

Mapping compressMappings(List<Mapping> mappings) {
  var res = Mapping();
  for (final m in mappings.reversed) {
    m.sortRanges();
    if (m.ranges.first.srcStart != 0) {
      m.ranges.insert(0, Range([0, 0, m.ranges.first.srcStart]));
    }
    m.ranges.add(
        Range([m.ranges.last.srcLast + 1, m.ranges.last.srcLast + 1, 1 << 35]));
    if (res.ranges.isEmpty) {
      res.ranges = m.ranges;
      continue;
    }
    final next = Mapping();
    for (Range r in m.ranges) {
      final lastMapping = res.ranges;
      int first = r.srcStart + r.offset;
      int last = r.srcLast + r.offset;
      int ri = 0;
      while (first <= last && ri < lastMapping.length) {
        if (first < lastMapping[ri].srcStart) {
          int nextLast = min(last, lastMapping[ri].srcStart - 1);
          next.ranges
              .add(Range([first, first - r.offset, nextLast - first + 1]));
          first = nextLast + 1;
        } else if (first > lastMapping[ri].srcLast) {
          print('breakkkkkkk');
          ri++;
        } else {
          int nextLast = min(last, lastMapping[ri].srcLast);
          next.ranges.add(Range([
            first + lastMapping[ri].offset,
            first - r.offset,
            nextLast - first + 1
          ]));
          first = nextLast + 1;
        }
      }
      if (ri == lastMapping.length) {
        next.ranges.add(Range([first, first - r.offset, last - first + 1]));
      }
    }
    next.sortRanges();
    // next.printRanges();
    // print('--');
    res = next;
  }

  return res;
}

class Mapping {
  List<Range> ranges = [];

  int _ri = 0;

  Range get curRange => ranges[_ri];
  void firstRange() => _ri = 0;
  void nextRange() => _ri++;
  bool get hasMoreRanges => _ri < numRanges - 1;
  int get numRanges => ranges.length;

  void sortRanges() => ranges.sort();

  void printRanges() => ranges.forEach(print);

  int getDest(int src) {
    int x = lowerBound(List.generate(ranges.length, (i) => i), src,
        compare: (i, s) => ranges[i].srcStart + ranges[i].range - 1 - s);

    if (x == ranges.length) return src;
    if (ranges[x].srcStart <= src) {
      return src + (ranges[x].destStart - ranges[x].srcStart);
    }
    return src;
  }
}

class Range implements Comparable<Range> {
  Range(List<int> input)
      : srcStart = input[1],
        destStart = input[0],
        range = input[2];
  int srcStart;
  int destStart;
  int range;

  int get srcLast => srcStart + range - 1;

  int get offset => destStart - srcStart;

  @override
  int compareTo(Range other) {
    return srcStart - other.srcStart;
  }

  @override
  String toString() => '$destStart $srcStart $range';
}
