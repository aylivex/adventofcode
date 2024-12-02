#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day02.txt")
    or die $!;

my $safe = 0;

input:
while (<INPUT>) {
    my @levels = split /\s+/;
    print "@levels\n";

    my $dir = 0;
    my $i = 0;
    do {{ # For next ..., just double the braces
          # https://perldoc.perl.org/perlsyn
        my $diff = $levels[$i] - $levels[$i + 1];
        if ($diff == 0 || $diff < -3 || $diff > 3
            || ($diff > 0 && $dir < 0) || ($diff < 0 && $dir > 0)) {
            next input;
        }
        $dir = $diff;
        print "\t$dir\n";
    }} while (++$i < $#levels);

    $safe++;
}

print "Safe: $safe\n";
