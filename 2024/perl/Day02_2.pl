#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day02.txt")
    or die $!;

sub is_safe(@) {
    my @levels = @_;

    my $dir = 0;
    my $i = 0;
    do {
        my $diff = $levels[$i] - $levels[$i + 1];
        if ($diff == 0 || $diff < -3 || $diff > 3
            || ($diff > 0 && $dir < 0) || ($diff < 0 && $dir > 0)) {
            return 0;
        }
        $dir = $diff;
    } while (++$i < $#levels);

    return 1;
}

my $safe = 0;

while (<INPUT>) {
    my @levels = split /\s+/;

    if (is_safe(@levels)) {
        $safe++;
    } else {
        for my $i (0 .. $#levels) {
            my @sub_levels = (@levels[0 .. $i - 1],
                              @levels[$i + 1 .. $#levels]);
            if (is_safe(@sub_levels)) {
                $safe++;
                last;
            }
        }
    }
}

print "Safe: $safe\n";
