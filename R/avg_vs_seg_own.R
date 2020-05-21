# Run with:
#  source("avg_vs_seg.R") 
#  then:
#     avg_vs_seg('a5.csv', '1711296', 'speed')
#     avg_vs_seg('a5.csv', '1711296', 'other')
#
#
avg_vs_seg <- function(data_file, ref_file, seg, measure) {

library(DAAG) # for pause())
library(scales)
library(ggplot2)

    # CSV with original data
    s <- read.csv(data_file)
    # Option 1: CSV with reverence data
    r <- read.csv(ref_file)
    # Option 2: combined fake-reference data to form a vector 
    v <- c(20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23,20,20,23,23,30,30,39,39,39,39,40,40,44,44,46,46,60,60,69,69,78,78,79,79,78,78,75,75,72,72,79,79,80,80,70,70,67,67,62,62,60,60,40,40,23,23,20,20,23,23)
    
    # dates starts in 2017-01-01 <-- Sunday
    # extract 'day_date' part of object s
    days <- as.data.frame(table(s$day_date))$Var1
    # create a list from the csv and take the ones with match 'osm_id' = 'seg'
    aa <- s[s$osm_id==seg,]
    # order the just created list by 'date_time'
    a <- aa[order(aa$date_time),]
  
    days2 <- as.data.frame(table(r$day_date))$Var1
    xx <- r[r$osm_id==seg,]
    x <- xx[order(xx$date_time),]
    
    # used by the print() function called cat()
    road_seg <- paste('A5-',seg,sep='')
    road_seg_fig <-paste('fig/',road_seg, '.pdf',sep='')        
    num_weeks <- round(dim(a)[1]/336)  # currently we have 336 datasets in a week
    num_weeks2 <- round(dim(x)[1]/336)  # num weeks = divide total num of datasets by 336 
    lane_number <- unique(a$lane_quantity)
    lane_number2 <- unique(x$lane_quantity)
    max_velocity <- max(as.numeric(a$avg_vs), na.rm=TRUE)
    max_velocity2 <- max(as.numeric(x$avg_vs), na.rm=TRUE)
    
    # print to console
    cat('Road A5','Segment:',seg, 'Num weeks:', num_weeks, 'Lane quantity:', lane_number, 'Max Speed:', max_velocity,  '\n')
    cat('Reference','Segment:',seg, 'Num weeks:', num_weeks2, 'Lane quantity:', lane_number2, 'Max Speed:', max_velocity2,  '\n')
    #pdf(road_seg_fig)
    
    # Option 1 [speed]: plot origianl csv - avg speednum of vehicles - not working-missing loop
    # Option 2 [car]: plot original csv - num of vehicles - not working-missing loop
    # Option 3 [reference]: plot ref datanum of vehicles - not working-missing loop
    # Option 4 [mixing]: plot original and ref data - avg speed
    # Option 5 [fake]: plot own fake vector - avg speed
    if(measure == 'speed') {
        max_speed <- max(as.numeric(a$avg_vs), na.rm=TRUE)
        plot(x=c(0,23),y=c(0,max_speed), main=paste(road_seg,'Avg Speed'), xlab="Hour/Day in a Week", ylab='Average Velocity' );
      } else if (measure == 'car') {
        max_autos_seg <- max(as.numeric(a$vehicle_count_per_segment), na.rm=TRUE)
        plot(x=c(0,23),y=c(0,max_autos_seg), main=paste(road_seg,'No. of vehicles'), xlab="Cars in a Week", ylab='Number of Vehicles' );
      } else if (measure == 'reference') {
        max_speed_ref <- max(as.numeric(x$avg_vs), na.rm=TRUE)
        plot(x=c(0,23),y=c(0,max_speed_ref), main=paste(road_seg,'Reference Speed'), xlab="Hours in a Week", ylab='Avg Velocity' );
      } 
      # else if (measure == 'mixing') {
      #   max_speed <- max(as.numeric(a$avg_vs), na.rm=TRUE)
      #   max_speed_ref <- max(as.numeric(x$avg_vs), na.rm=TRUE)
      #   plot(x=c(0,23),y=c(0,max_speed_ref), main=paste(road_seg,'Reference Speed'), xlab="x in a time", ylab='Avg Velocity' );
      #   plot(x=c(0,23),y=c(0,max_speed), type = 'b', col = 'blue', main=paste(road_seg,'TEST'), xlab="Hour/Day in a Week", ylab='Average Velocity');
      # } 
      else {
      # tbd
      }

      
      # !!! Missing IF_ELSE !!!! To select one of the foor loops - TODO
      for(i in seq(1, length(days2)-7, by=7) ){
        week <- a[as.Date(a$day_date)>=as.Date(days[i]) & as.Date(a$day_date)<=as.Date(days[i+6]),]
        week2 <- x[as.Date(x$day_date)>=as.Date(days2[i]) & as.Date(x$day_date)<=as.Date(days2[i+6]),]
        for(j in seq(1, length(days2)-7, by=7) ){
          #  get one week of data per segment - original data
          week <- a[as.Date(a$day_date)>=as.Date(days[j]) & as.Date(a$day_date)<=as.Date(days[j+6]),]
          # ref data
          week2 <- x[as.Date(x$day_date)>=as.Date(days2[j]) & as.Date(x$day_date)<=as.Date(days2[j+6]),]
          
          # plotting ref data (changing each week) to use with 'mixing'
          plot(week2$avg_vs, type = "b", col='green', main=paste(road_seg,'Incl. Reference Speed'), xlab="Hours in a Week", ylab='Avg Velocity' )
          # plotting ref data (changing each week) to use with 'fake'
          # plot(week$avg_vs, type = "b", col='green', main=paste(road_seg,'Incl. Reference Speed'), xlab="Hours in a Week", ylab='Avg Velocity' )
          
          
          
          # Print to console (Todo: adjustement)
          first_day <- weekdays(as.Date(days2[i]))
          last_day <- weekdays(as.Date(days2[i+6]))
          #print(week$vehicle_count_per_segment)
          null_values <- max(week$vehicle_count_per_segment)
          #print(is.na(null_values))
          if(is.na(null_values)){
            cat(i,' Date Ref:',as.character(days2[i]),first_day, 'to Date Ref:', as.character(days2[i+6]),last_day, 'WEEK HAS NAs\n')
          } else {
            cat(i,' Date Ref:',as.character(days2[i]),first_day, 'to Date Ref:', as.character(days2[i+6]),last_day, '\n')
          }
          
          # Plotting
          if(measure == 'mixing') {
            lines(week$avg_vs, col='violet')    
          } else if(measure == 'fake') {
            lines(v, type = "l", col='red')
          } else {
            #lines(week$avg_vs, type = "l", col='orange')
          }
          #pause()
        }
      }
    
    ### PRINT TO CONSOLE  - ###
    # - 7 because for-loop-steps are 7 (days)
    # for-loop brings up 7 days each loop incl. First day and last day
    for(i in seq(1, length(days)-7, by=7) ){                          
      #  get one week of data per segment
      week <- a[as.Date(a$day_date)>=as.Date(days[i]) & as.Date(a$day_date)<=as.Date(days[i+6]),]
      first_day <- weekdays(as.Date(days[i]))
      last_day <- weekdays(as.Date(days[i+6]))    
      
      #print(week$vehicle_count_per_segment)
      null_values <- max(week$vehicle_count_per_segment)
      # is.na() checks wheather value is missing (TRUE) or not (FALSE)
      # reason to be TRUE - car accident
      #print(is.na(null_values))
      # if there is a missing value print to console
      if(is.na(null_values)){
        cat(i,' Date:',as.character(days[i]),first_day, 'to Date:', as.character(days[i+6]),last_day, 'WEEK HAS NAs\n')
      } else {    
        cat(i,' Date:',as.character(days[i]),first_day, 'to Date:', as.character(days[i+6]),last_day, '\n')
      }
      if(measure == 'speed') {
        lines(week$avg_vs, col='blue')
      } else if(measure == 'reference'){
        lines(week2$avg_vs, col='orange')
      }else {
        lines(week$vehicle_count_per_segment, col='blue')
      }
      # pause()
    }
      # to save the plot
      #dev.off()
      #cat('Plot saved in:', road_seg_fig, '\n')
}
