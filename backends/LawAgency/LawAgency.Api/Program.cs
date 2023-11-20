using LawAgency.Api;
using LawAgency.Api.Context;
using Microsoft.EntityFrameworkCore;
const string policyName = "AgencyPolicy";
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDependencies(builder.Configuration);
builder.Services.AddSwaggerGen();
builder.Services.AddAuthentication();
builder.Services.AddAuthorization();
builder.Services.AddDbContext<AgencyContext>(options =>
    options.UseNpgsql(
        builder.Configuration.GetConnectionString("DefaultConnection")
    ).UseNpgsql(
        b => b.MigrationsAssembly(typeof(AgencyContext).Assembly.FullName)
    )
);

builder.Services
    .AddCors(options =>
    {
        options.AddPolicy(policyName,
            b => b.WithOrigins(builder.Configuration["AllowedHosts"]!)
                .AllowAnyOrigin()
                .AllowAnyHeader()
                .AllowAnyMethod()); 
    });
var app = builder.Build();
app.MigrateDb<AgencyContext>();
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseHttpsRedirection();
app.UseCors(policyName);
app.MapEndpoints();
app.UseAuthentication();
app.UseAuthorization();
app.Run();
