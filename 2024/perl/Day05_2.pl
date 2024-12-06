#!perl
use warnings;
use strict;

open(INPUT, '<', "input.txt")
    or die $!;

my %reverse;
while (<INPUT>) {
    last if /^$/;
    (my $left, my $right) = /(\d+)\|(\d+)/;
    if (defined $reverse{$right}) {
        push @{$reverse{$right}}, $left;
    } else {
        $reverse{$right} = [ $left ];
    }
}

my @pages;
while (<INPUT>) {
    chop;
    my @page_list = split /,/;
    push @pages, \@page_list;
}

my $middle_sum = 0;

for my $update (@pages) {
    my @update_pages = @{$update};
#    print "@update_pages\n";
    
    my $corrected = 0;

    my $valid;
    do {
        $valid = 1;

        my $i = $#update_pages;
        while ($i > 0 && $valid) {
            my $left = $update_pages[$i];
#            print "  $left ($i)\n";
            my $j = $i - 1;
            while ($j >= 0 && $valid) {
                my $right = $update_pages[$j];
#                print "    $right ($j)\n";
                # if (defined $ordering{$left}) {
                #     for my $page (@{$ordering{$left}}) 
                    
                # }
                if ($i > 0 && defined $reverse{$right}) {
                    for my $page (@{$reverse{$right}}) {
                        # print "      $page";
                        if ($page == $left) {
                            $update_pages[$i] = $right;
                            $update_pages[$j] = $left;
                            $valid = 0;
                            $corrected++;
                            last;
                        }
                        # print " - $valid\n";
                    }
                }
                --$j;
            }
            --$i;
        }
#        print ($valid ? "  valid" : "invalid");
#        print "\n";
    } while (!$valid);

    if ($corrected > 0) {
#        print "middle: ";
        my $middle = $update_pages[($#update_pages + 1) / 2];
#        print "$middle\n";
        $middle_sum += $middle;
    }

    # print "@update_pages\n";
}

print "Answer: $middle_sum\n";
