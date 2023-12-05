#!perl
use warnings;
use strict;

# Number of cubes in the bag
my %bag = (
    'red' => 12,
    'green' => 13,
    'blue' => 14
);

my $gameIdSum = 0;


open(INPUT, '<', "2023/input/day02.txt")
    or die $!;

while (<INPUT>) {
    (my $gameId) = /Game (\d+):/;

    # Remove the game id;
    s/.*: //;

    my %game = ('red' => 0, 'green' => 0, 'blue' => 0);
    foreach (split(/[,;] ?/)) {
        (my $number, my $colour) = /(\d+) (\S+)/;
        $game{$colour} += $number;
    }

    if ($game{'red'} <= $bag{'red'}
        && $game{'green'} <= $bag{'green'}
        && $game{'blue'} <= $bag{'blue'}) {
        $gameIdSum += $gameId;
    }
}

print "$gameIdSum\n";
