import {Component, Input, OnInit} from '@angular/core';
import * as moment from 'moment';
import {STATUS_COLORS, TIME_FORMAT, USER_ROLES} from '../../constants';
import {EventsService} from '../../services/events/events.service';
import {AuthService} from '../../services/auth/auth.service';

declare var $: any;


@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {
  @Input() openDialog;
  @Input() update;
  @Input() openDeleteDialog;
  @Input() getTableData;

  constructor(private eventService: EventsService, private authService: AuthService) {
  }


  ngOnInit(): void {


    $('#eventTableList').DataTable({
      searching: false,
      serverSide: true,
      processing: true,
      lengthChange: false,
      rowCallback: (row: Node, data: any[]) => {
        $('td i[edit]', row).unbind('click');
        $('td i[edit]', row).bind('click', () => {
          this.openDialog(data);
        });
        $('td i[delete]', row).unbind('click');
        $('td i[delete]', row).bind('click', () => {
          this.openDeleteDialog(data);
        });
        return row;
      },
      ajax: this.getTableData,
      columns: [
        {
          name: 'from',
          title: 'From',
          render: (data, type, row) => {
            return moment(row.start_date).format(TIME_FORMAT);
          },
        },
        {
          name: 'to',
          title: 'To',
          render: (data, type, row) => {
            return moment(row.end_date).format(TIME_FORMAT);
          },
        },
        {
          name: 'patient',
          render: (data, type, row) => {
            return (
              '<a href="/card/' + row.patient.id + '">'
              + row.patient.first_name + ' '
              + row.patient.second_name + ' '
              + row.patient.last_name +
              '</a>');
          },
          title: 'Patient'
        },
        {
          name: 'procedure',
          title: 'Procedure',
          data: 'procedure.description'
        },
        {
          name: 'room',
          title: 'Room',
          data: 'room.description'
        },
        {
          name: 'doctor',
          render: (data, type, row) => {
            return row.staff.first_name + ' ' + row.staff.second_name + ' ' + row.staff.last_name;
          },
          title: 'Doctor'
        },
        {
          name: 'status',
          data: 'status',
          title: 'Status',
          render: (data, type, row) => {
            return ('<a class="ui ' + STATUS_COLORS[row.status] + ' label">' + row.status + '</a>');
          },
        },
        {
          orderable: false,
          className: 'ui center aligned',
          render: () => {
            return ('<i class="edit link icon" edit></i>');
          },
        },
        {
          orderable: false,
          className: 'ui center aligned',
          visible: this.authService.userHasRole(USER_ROLES.ROLE_ADMIN),
          render: () => {
            return ('<i class="trash link icon" delete></i>');
          },
        },
      ]
    });
  }
}
