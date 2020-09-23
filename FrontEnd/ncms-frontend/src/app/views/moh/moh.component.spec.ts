import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MohComponent } from './moh.component';

describe('MohComponent', () => {
  let component: MohComponent;
  let fixture: ComponentFixture<MohComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MohComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MohComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
